create database kuberix_crm_single character set utf8mb4 collate utf8mb4_general_ci;
use kuberix_crm_single;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(0) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(0) NOT NULL,
  `log_created` datetime(0) NOT NULL,
  `log_modified` datetime(0) NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for wk_admin_attention
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_attention`;
CREATE TABLE `wk_admin_attention`  (
  `attention_id` int(0) NOT NULL AUTO_INCREMENT,
  `be_user_id` bigint(0) NOT NULL COMMENT 'Follower',
  `attention_user_id` bigint(0) NOT NULL COMMENT 'Follow people',
  PRIMARY KEY (`attention_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Address Book User Concern Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_attention
-- ----------------------------
INSERT INTO `wk_admin_attention` VALUES (1, 14773, 14773);

-- ----------------------------
-- Table structure for wk_admin_config
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_config`;
CREATE TABLE `wk_admin_config`  (
  `setting_id` int(0) NOT NULL AUTO_INCREMENT,
  `status` int(0) NOT NULL DEFAULT 0 COMMENT 'Status, 0: not enabled 1: enabled',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Set name',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Value',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Describe',
  PRIMARY KEY (`setting_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 262462 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Customer rules' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_config
-- ----------------------------
INSERT INTO `wk_admin_config` VALUES (262433, 1, 'expiringContractDays', '3', 'Contract expiry reminder');
INSERT INTO `wk_admin_config` VALUES (262434, 1, 'putInPoolRemindDays', '2', NULL);
INSERT INTO `wk_admin_config` VALUES (262435, 1, 'taskExamine', '1', 'Task approval');
INSERT INTO `wk_admin_config` VALUES (262436, 1, 'log', '1', 'Log');
INSERT INTO `wk_admin_config` VALUES (262437, 1, 'book', '1', 'Address book');
INSERT INTO `wk_admin_config` VALUES (262438, 1, 'crm', '1', 'Customer management');
INSERT INTO `wk_admin_config` VALUES (262439, 1, 'project', '1', 'Project management');
INSERT INTO `wk_admin_config` VALUES (262440, 1, 'calendar', '1', 'Calendar');
INSERT INTO `wk_admin_config` VALUES (262441, 1, 'email', '1', 'Mail');
INSERT INTO `wk_admin_config` VALUES (262442, 1, 'knowledge', '1', 'Knowledge base');
INSERT INTO `wk_admin_config` VALUES (262443, 1, 'hrm', '1', 'Human resource Management');
INSERT INTO `wk_admin_config` VALUES (262444, 1, 'jxc', '1', 'Invoicing management');
INSERT INTO `wk_admin_config` VALUES (262445, 1, 'call', '3', 'Call Center');
INSERT INTO `wk_admin_config` VALUES (262446, 0, 'followRecordOption', 'Call up', 'Follow up recording options');
INSERT INTO `wk_admin_config` VALUES (262447, 0, 'followRecordOption', 'Send email', 'Follow up recording options');
INSERT INTO `wk_admin_config` VALUES (262448, 0, 'followRecordOption', 'Texting', 'Follow up recording options');
INSERT INTO `wk_admin_config` VALUES (262449, 0, 'followRecordOption', 'Meet and greet', 'Follow up recording options');
INSERT INTO `wk_admin_config` VALUES (262450, 0, 'followRecordOption', 'Activity', 'Follow up recording options');
INSERT INTO `wk_admin_config` VALUES (262451, 0, 'logWelcomeSpeech', 'The blue sky is quiet, the air is fresh, the sun is bright', 'Default log welcome');
INSERT INTO `wk_admin_config` VALUES (262452, 0, 'logWelcomeSpeech', 'The happiest thing in life is struggle', 'Default log welcome');
INSERT INTO `wk_admin_config` VALUES (262453, 0, 'logWelcomeSpeech', 'After a hard day\'s work, the happiest thing in life is struggle', 'Default log welcome');
INSERT INTO `wk_admin_config` VALUES (262454, 0, 'pictureSetting', NULL, 'Field check-in photo upload settings');
INSERT INTO `wk_admin_config` VALUES (262455, 0, 'returnVisitRemindConfig', '7', 'Customer return visit reminder settings');
INSERT INTO `wk_admin_config` VALUES (262456, 0, 'numberSetting', '6', 'Auto numbering settings');
INSERT INTO `wk_admin_config` VALUES (262457, 0, 'numberSetting', '7', 'Auto numbering settings');
INSERT INTO `wk_admin_config` VALUES (262458, 0, 'numberSetting', '17', 'Auto numbering settings');
INSERT INTO `wk_admin_config` VALUES (262459, 0, 'numberSetting', '18', 'Auto numbering settings');
INSERT INTO `wk_admin_config` VALUES (262460, 1, 'companyInfo', '{\"companyLogo\":\"\",\"companyName\":\"Conscience Enterprise\"}', 'Enterprise LOGO configuration');
INSERT INTO `wk_admin_config` VALUES (262461, 1, 'marketing', NULL, 'Whether to start marketing activities');

-- ----------------------------
-- Table structure for wk_admin_dept
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_dept`;
CREATE TABLE `wk_admin_dept`  (
  `dept_id` int(0) NOT NULL AUTO_INCREMENT,
  `pid` int(0) NULL DEFAULT 0 COMMENT 'Parent ID top-level department is 0',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Department name',
  `num` int(0) NULL DEFAULT NULL COMMENT 'Sort the bigger the later',
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Department Notes',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Department head',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14853 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Department table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_dept
-- ----------------------------
INSERT INTO `wk_admin_dept` VALUES (14852, 0, 'Company-wide', 1, '', NULL);

-- ----------------------------
-- Table structure for wk_admin_file
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_file`;
CREATE TABLE `wk_admin_file`  (
  `file_id` bigint(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Accessory name',
  `size` bigint(0) NOT NULL COMMENT 'Attachment size (bytes)',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'File path',
  `file_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'file' COMMENT 'File type,file,img',
  `type` int(0) NULL DEFAULT NULL COMMENT '1 Local 2 Spring Cloud oss',
  `source` int(0) NULL DEFAULT NULL COMMENT 'Source 0 Default 1 admin 2 crm 3 work 4 oa 5 Invoicing 6 hrm',
  `is_public` int(0) NULL DEFAULT 0 COMMENT '1 public access 0 private access',
  `batch_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Batch id',
  PRIMARY KEY (`file_id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Attachment table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_file
-- ----------------------------

-- ----------------------------
-- Table structure for wk_admin_login_log
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_login_log`;
CREATE TABLE `wk_admin_login_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `create_user_id` bigint(0) NOT NULL COMMENT 'Operator id',
  `login_time` datetime(0) NOT NULL COMMENT 'Log in time',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Login ip address',
  `login_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Login location',
  `device_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Equipment type',
  `core` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Terminal kernel',
  `platform` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Platform',
  `imei` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IEMI device number',
  `auth_result` int(0) NULL DEFAULT NULL COMMENT 'Authentication result 1 success 2 failure',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'System login log table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for wk_admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_menu`;
CREATE TABLE `wk_admin_menu`  (
  `menu_id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Menu ID',
  `parent_id` int(0) UNSIGNED NULL DEFAULT 0 COMMENT 'Parent menu ID',
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Menu name',
  `realm` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Authority ID',
  `realm_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Authority URL',
  `realm_module` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Owning module',
  `menu_type` int(0) NULL DEFAULT NULL COMMENT 'Menu Type 1 Directory 2 Menu 3 Button 4 Special',
  `sort` int(0) UNSIGNED NULL DEFAULT 0 COMMENT 'Sort (valid at the same level)',
  `status` int(0) NULL DEFAULT 1 COMMENT 'Status 1 Enable 0 Disable',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Menu description',
  PRIMARY KEY (`menu_id`) USING BTREE,
  INDEX `menu_id`(`menu_id`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  INDEX `realm`(`realm`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 943 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Backstage menu table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_menu
-- ----------------------------
INSERT INTO `wk_admin_menu` VALUES (1, 0, 'All', 'crm', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (2, 0, 'All', 'bi', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (3, 0, 'All', 'manage', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (5, 0, 'All', 'hrm', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (6, 0, 'All', 'jxc', '', NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (9, 1, 'Lead management', 'leads', NULL, NULL, 1, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (10, 1, 'Customer management', 'customer', NULL, NULL, 1, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (11, 1, 'Contact management', 'contacts', NULL, NULL, 1, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (12, 1, 'Opportunity Management', 'business', NULL, NULL, 1, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (13, 1, 'Contract management', 'contract', NULL, NULL, 1, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (14, 1, 'Payment management', 'receivables', NULL, NULL, 1, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (15, 1, 'Product management', 'product', NULL, NULL, 1, 7, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (17, 9, 'New', 'save', '/crmLeads/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (18, 9, 'Edit', 'update', '/crmLeads/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (19, 9, 'View list', 'index', '/crmLeads/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (20, 9, 'Check the details', 'read', '/crmLeads/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (21, 9, 'Import', 'excelimport', '/crmLead/downloadExcel', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (22, 9, 'Export', 'excelexport', '/crmLead/allExportExcel,/crmLead/batchExportExcel', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (23, 9, 'Delete', 'delete', '/crmLead/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (24, 9, 'Transfer', 'transfer', '/crmLeads/changeOwnerUser', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (25, 9, 'Convert', 'transform', '/crmLead/transfer', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (26, 10, 'New', 'save', '/crmCustomer/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (27, 10, 'Edit', 'update', '/crmCustomer/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (28, 10, 'View list', 'index', '/crmCustomer/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (29, 10, 'Check the details', 'read', '/crmCustomer/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (30, 10, 'Import', 'excelimport', '/crmCustomer/uploadExcel', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (31, 10, 'Export', 'excelexport', '/crmCustomer/batchExportExcel,/crmCustomer/allExportExcel', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (32, 10, 'Delete', 'delete', '/crmCustomer/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (33, 10, 'Transfer', 'transfer', '/crmCustomer/changeOwnerUser', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (34, 10, 'Into the open sea', 'putinpool', '/crmCustomer/updateCustomerByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (35, 10, 'Lock/unlock', 'lock', '/crmCustomer/lock', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (36, 10, 'Editorial team members', 'teamsave', '/crmCustomer/addMembers,/crmCustomer/updateMembers,/crmCustomer/exitTeam', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (40, 11, 'New', 'save', '/crmContacts/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (41, 11, 'Edit', 'update', '/crmContacts/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (42, 11, 'View list', 'index', '/crmContacts/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (43, 11, 'Check the details', 'read', '/crmContacts/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (44, 11, 'Delete', 'delete', '/crmContacts/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (45, 11, 'Transfer', 'transfer', '/crmContacts/changeOwnerUser', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (46, 12, 'New', 'save', '/crmBusiness/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (47, 12, 'Edit', 'update', '/crmBusiness/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (48, 12, 'View list', 'index', '/crmBusiness/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (49, 12, 'Check the details', 'read', '/crmBusiness/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (50, 12, 'Delete', 'delete', '/crmBusiness/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (51, 12, 'Transfer', 'transfer', '/crmBusiness/changeOwnerUser', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (52, 12, 'Editorial team members', 'teamsave', '/crmBusiness/addMembers,/crmBusiness/updateMembers,/crmBusiness/deleteMembers', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (53, 13, 'New', 'save', '/crmContract/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (54, 13, 'Edit', 'update', '/crmContract/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (55, 13, 'View list', 'index', '/crmContract/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (56, 13, 'Check the details', 'read', '/crmContract/queryById', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (57, 13, 'Delete', 'delete', '/crmContract/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (58, 13, 'Transfer', 'transfer', '/crmContract/changeOwnerUser', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (59, 13, 'Editorial team members', 'teamsave', '/crmContract/addMembers,/crmContract/updateMembers,/crmContract/deleteMembers', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (60, 14, 'New', 'save', '/crmReceivables/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (61, 14, 'Edit', 'update', '/crmReceivables/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (62, 14, 'View list', 'index', '/crmReceivables/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (63, 14, 'Check the details', 'read', '/crmReceivables/queryById', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (64, 14, 'Delete', 'delete', '/crmReceivables/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (65, 15, 'New', 'save', '/crmProduct/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (66, 15, 'Edit', 'update', '/crmProduct/udpate', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (67, 15, 'View list', 'index', '/crmProduct/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (68, 15, 'Check the details', 'read', '/crmProduct/queryById', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (69, 15, 'On/Off', 'status', '/crmProduct/updateStatus', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (70, 15, 'Transfer', 'transfer', '/crmProduct/changeOwnerUser', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (71, 14, 'Transfer', 'transfer', '/crmReceivables/changeOwnerUser', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (97, 2, 'Achievement of performance goals', 'achievement', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (98, 2, 'Sales funnel', 'business', '/biFunnel/addBusinessAnalyze,/biFunnel/win', NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (99, 2, 'Employee Customer Analysis', 'customer', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (101, 2, 'Employee performance analysis', 'contract', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (102, 97, 'Check', 'read', '/bi/taskCompleteStatistics', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (103, 98, 'Check', 'read', '', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (104, 99, 'Check', 'read', '/biCustomer/totalCustomerStats,/biCustomer/totalCustomerTable,/biCustomer/customerRecordStats,/biCustomer/customerRecordStats,/biCustomer/customerRecodCategoryStats,/biCustomer/customerConversionStats,/biCustomer/poolStats,/biCustomer/poolTable,/biCustomer/employeeCycle,/biCustomer/employeeCycleInfo,/biCustomer/customerSatisfactionTable,/biCustomer/productSatisfactionTable', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (106, 101, 'Check', 'read', '/biEmployee/contractNumStats,/biEmployee/contractMoneyStats,/biEmployee/receivablesMoneyStats,/biEmployee/totalContract,/biEmployee/invoiceStats', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (107, 11, 'Contact export', 'excelexport', '/crmContacts/batchExportExcel,/crmContacts/allExportExcel', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (108, 11, 'Contact import', 'excelimport', '/crmContacts/uploadExcel', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (109, 15, 'Product introduction', 'excelimport', '/crmProduct/uploadExcel', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (110, 15, 'Product export', 'excelexport', '/crmProduct/batchExportExcel,/crmProduct/allExportExcel', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (117, 2, 'Product analysis', 'product', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (118, 117, 'Check', 'read', '/bi/productStatistics,/biRanking/contractProductRanKing', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (123, 2, 'Customer profile analysis', 'portrait', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (124, 123, 'Check', 'read', '/biRanking/addressAnalyse,/biRanking/portrait,/biRanking/portraitLevel,/biRanking/portraitSource', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (125, 2, 'Leaderboard', 'ranking', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (126, 125, 'Check', 'read', '/biRanking/contractRanKing,/biRanking/receivablesRanKing,/biRanking/contractCountRanKing,/biRanking/productCountRanKing,/biRanking/productCountRanKing,/biRanking/contactsCountRanKing,/biRanking/customerCountRanKing,/biRanking/recordCountRanKing,/biRanking/customerGenjinCountRanKing,/biRanking/customerGenjinCountRanKing', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (146, 2, 'Office Analysis', 'oa', NULL, NULL, 1, 10, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (147, 146, 'Check', 'read', '/biWork/logStatistics,/biWork/examineStatistics', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (148, 0, 'All', 'oa', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (149, 0, 'All', 'project', NULL, NULL, 1, 0, 1, 'Project management role permissions');
INSERT INTO `wk_admin_menu` VALUES (150, 148, 'Address book', 'book', NULL, NULL, 1, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (151, 150, 'Check', 'read', '/adminUser/queryListName', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (152, 149, 'Project management', 'projectManage', NULL, NULL, 1, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (153, 152, 'New Project', 'save', '/work/addWork', NULL, 3, 2, 1, 'projectSave');
INSERT INTO `wk_admin_menu` VALUES (160, 3, 'Home', 'system', NULL, NULL, 1, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (161, 160, 'Check', 'read', '', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (162, 160, 'Edit', 'update', '/adminConfig/setAdminConfig', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (163, 3, 'Application management', 'configSet', NULL, NULL, 1, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (164, 163, 'Check', 'read', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (165, 163, 'Disable/enable', 'update', '/adminConfig/setModuleSetting', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (166, 3, 'Staff and department management', 'users', NULL, NULL, 1, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (167, 166, 'Department/Employee View', 'read', '', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (168, 166, 'New employee', 'userSave', '/adminUser/addUser', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (169, 166, 'Employee Disable/Activate', 'userEnables', '/adminUser/setUserStatus', NULL, 3, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (170, 166, 'Staff operation', 'userUpdate', '/adminUser/setUser', NULL, 3, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (171, 166, 'New department', 'deptSave', '/adminDept/addDept', NULL, 3, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (172, 166, 'Department Editor', 'deptUpdate', '/adminDept/setDept', NULL, 3, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (173, 166, 'Department delete', 'deptDelete', '/adminDept/deleteDept', NULL, 3, 7, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (174, 3, 'Role rights management', 'permission', NULL, NULL, 1, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (175, 174, 'Role permission settings', 'update', '/adminRole/getRoleByType/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (176, 3, 'Office approval flow', 'oa', NULL, NULL, 1, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (177, 176, 'Office approval flow management', 'examine', '/oaExamineCategory/queryExamineCategoryList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (178, 3, 'Business approval flow', 'examineFlow', NULL, NULL, 1, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (179, 178, 'Business approval flow management', 'update', '/crmExamine/queryAllExamine', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (180, 3, 'Customer Management Settings', 'crm', NULL, NULL, 1, 7, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (181, 180, 'Custom Field Settings', 'field', '/crmField/queryFields', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (182, 180, 'Customer High Seas Rules', 'pool', '/crmCustomerPool/queryPoolSettingList', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (183, 180, 'Business parameter settings', 'setting', '', NULL, 3, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (184, 180, 'Performance goal setting', 'achievement', '/biAchievement/queryAchievementList', NULL, 3, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (185, 3, 'Project management settings', 'work', NULL, NULL, 1, 8, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (186, 185, 'Project management', 'update', '', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (187, 148, 'Announcement', 'announcement', NULL, NULL, 1, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (188, 187, 'New', 'save', '/oaAnnouncement/addAnnouncement', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (189, 187, 'Edit', 'update', '/oaAnnouncement/setAnnouncement', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (190, 187, 'Delete', 'delete', '/oaAnnouncement/delete', NULL, 3, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (191, 10, 'Set transaction status', 'dealStatus', '/crmCustomer/setDealStatus', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (192, 13, 'Contract void', 'discard', '/crmContract/contractDiscard', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (193, 2, 'Call Center', 'call', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (194, 193, 'Query call records', 'index', NULL, NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (195, 193, 'Call log analysis', 'analysis', NULL, NULL, 3, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (200, 1, 'Marketing activity', 'marketing', NULL, NULL, 1, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (201, 200, 'New', 'save', '/crmMarketing/add', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (202, 200, 'View list', 'index', '/crmMarketing/queryPageList', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (204, 200, 'Edit', 'update', '/crmMarketing/update', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (205, 200, 'Delete', 'delete', '/crmMarketing/deleteByIds', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (206, 200, 'Enable/disable', 'updateStatus', '/crmMarketing/updateStatus', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (207, 200, 'Check the details', 'read', '/crmMarketing/queryById', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (208, 13, 'Export', 'excelexport', '/crmContract/batchExportExcel,/crmContract/allExportExcel', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (209, 12, 'Export', 'excelexport', '/crmBusiness/batchExportExcel,/crmBusiness/allExportExcel', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (211, 15, 'Delete', 'delete', '/crmProduct/deleteByIds', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (212, 14, 'Export', 'excelexport', '/crmReceivables/batchExportExcel,/crmReceivables/allExportExcel', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (213, 1, 'Fieldwork', 'outwork', NULL, NULL, 1, 9, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (214, 213, 'New', 'save', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (215, 213, 'Check', 'read', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (216, 213, 'Delete', 'delete', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (217, 213, 'Set up', 'setting', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (218, 10, 'Nearby customers', 'nearbyCustomer', '/crmCustomer/nearbyCustomer', NULL, 3, 1, 1, '');
INSERT INTO `wk_admin_menu` VALUES (230, 3, 'System log', 'adminLog', NULL, NULL, 1, 9, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (231, 230, 'System login log', 'loginLog', '/system/log/queryLoginLogList', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (232, 230, 'Data operation log', 'actionRecord', '/system/log/queryActionRecordList', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (233, 230, 'System operation log', 'systemLog', '/system/log/querySystemLogList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (300, 0, 'Project management', 'work', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (301, 300, 'Project', 'project', NULL, NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (310, 301, 'Project settings', 'setWork', '', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (311, 301, 'Project export', 'excelExport', '', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (312, 301, 'New task list', 'saveTaskClass', '', NULL, 3, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (313, 301, 'Edit task list', 'updateTaskClass', '', NULL, 3, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (314, 301, 'Mobile task list', 'updateClassOrder', '', NULL, 3, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (315, 301, 'Delete task list', 'deleteTaskClass', '', NULL, 3, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (316, 301, 'New task', 'saveTask', '', NULL, 3, 7, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (317, 301, 'Mission accomplished', 'setTaskStatus', '', NULL, 3, 8, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (318, 301, 'Edit task title', 'setTaskTitle', '', NULL, 3, 9, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (319, 301, 'Edit task description', 'setTaskDescription', '', NULL, 3, 10, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (320, 301, 'Assignments', 'setTaskMainUser', '', NULL, 3, 11, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (321, 301, 'Set task time', 'setTaskTime', '', NULL, 3, 12, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (322, 301, 'Set task labels', 'setTaskLabel', '', NULL, 3, 13, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (323, 301, 'Add task participants', 'setTaskOwnerUser', '', NULL, 3, 14, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (324, 301, 'Set task priority', 'setTaskPriority', '', NULL, 3, 15, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (325, 301, 'Mobile task', 'setTaskOrder', '', NULL, 3, 16, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (326, 301, 'Archiving tasks', 'archiveTask', '', NULL, 3, 17, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (327, 301, 'Delete task', 'deleteTask', '', NULL, 3, 18, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (328, 301, 'Delete the task completely', 'cleanTask', '', NULL, 3, 19, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (329, 301, 'Add attachments to tasks', 'uploadTaskFile', '', NULL, 3, 20, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (330, 301, 'Task delete attachment', 'deleteTaskFile', '', NULL, 3, 21, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (331, 301, 'Project import', 'excelImport', '', NULL, 3, 22, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (332, 301, 'New subtask', 'addChildTask', '', NULL, 3, 23, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (333, 301, 'Edit subtasks', 'updateChildTask', '', NULL, 3, 24, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (334, 301, 'Delete subtask', 'deleteChildTask', '', NULL, 3, 25, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (335, 301, 'Recovery task', 'restoreTask', '', NULL, 3, 26, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (336, 301, 'Related business', 'saveTaskRelation', '', NULL, 3, 27, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (337, 301, 'Complete subtasks', 'setChildTaskStatus', '', NULL, 3, 28, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (400, 1, 'Customer return visit management', 'visit', NULL, NULL, 1, 8, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (401, 400, 'New', 'save', '/crmReturnVisit/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (402, 400, 'Edit', 'update', '/crmReturnVisit/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (403, 400, 'View list', 'index', '/crmReturnVisit/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (404, 400, 'Check the details', 'read', '/crmReturnVisit/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (405, 400, 'Delete', 'delete', '/crmReturnVisit/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (420, 1, 'Invoice management', 'invoice', NULL, NULL, 1, 9, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (421, 420, 'New', 'save', '/crmInvoice/save', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (422, 420, 'Edit', 'update', '/crmInvoice/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (423, 420, 'View list', 'index', '/crmInvoice/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (424, 420, 'Check the details', 'read', '/crmInvoice/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (425, 420, 'Delete', 'delete', '/crmInvoice/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (426, 420, 'Transfer', 'transfer', '/crmInvoice/changeOwnerUser', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (427, 420, 'Mark billing', 'updateInvoiceStatus', '/crmInvoice/updateInvoiceStatus', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (428, 420, 'Reset billing information', 'resetInvoiceStatus', '/crmInvoice/resetInvoiceStatus', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (440, 1, 'Follow up records management', 'followRecord', NULL, NULL, 1, 10, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (441, 440, 'Check', 'read', '/crmInstrument/queryRecordList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (442, 440, 'New', 'save', '/crmActivity/addCrmActivityRecord', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (443, 440, 'Edit', 'update', '/crmActivity/updateActivityRecord', NULL, 3, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (444, 440, 'Delete', 'delete', '/crmActivity/deleteCrmActivityRecord/*', NULL, 3, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (500, 180, 'Print template settings', 'print', '', NULL, 3, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (501, 12, 'Print', 'print', '', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (502, 13, 'Print', 'print', '', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (503, 14, 'Print', 'print', '', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (601, 6, 'Supplier', 'supplier', '', NULL, 1, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (602, 601, 'New', 'save', '/jxcSupplier/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (603, 601, 'Edit', 'update', '/jxcSupplier/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (604, 601, 'View list', 'index', '/jxcField/queryPageList/2', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (605, 601, 'Check the details', 'read', '/jxcField/information/2,/jxcSupplier/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (606, 601, 'Delete', 'delete', '/jxcSupplier/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (607, 601, 'Transfer', 'transfer', '/jxcField/transfer/2', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (608, 601, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (610, 6, 'Purchase order', 'purchase', '', NULL, 1, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (611, 610, 'New', 'save', '/jxcPurchase/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (612, 610, 'Edit', 'update', '/jxcPurchase/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (613, 610, 'View list', 'index', '/jxcField/queryPageList/3', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (614, 610, 'Check the details', 'read', '/jxcField/information/3,/jxcPurchase/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (615, 610, 'Delete', 'delete', '/jxcPurchase/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (616, 610, 'Transfer', 'transfer', '/jxcField/transfer/3', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (617, 610, 'Void', 'setState', '/jxcPurchase/setStateByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (618, 610, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (620, 6, 'Purchase returns', 'retreat', '', NULL, 1, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (621, 620, 'New', 'save', '/jxcRetreat/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (622, 620, 'Edit', 'update', '/jxcRetreat/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (623, 620, 'View list', 'index', '/jxcField/queryPageList/4', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (624, 620, 'Check the details', 'read', '/jxcField/information/4,/jxcRetreat/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (625, 620, 'Delete', 'delete', '/jxcRetreat/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (626, 620, 'Transfer', 'transfer', '/jxcField/transfer/4', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (627, 620, 'Void', 'setState', '/jxcRetreat/setStateByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (628, 620, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (630, 6, 'Product', 'product', '', NULL, 1, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (631, 630, 'New', 'save', '/jxcProduct/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (632, 630, 'Edit', 'update', '/jxcProduct/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (633, 630, 'View list', 'index', '/jxcField/queryList/1,/jxcField/queryPageList/1,/jxcWarehouseProduct/queryPageList,/jxcWarehouseProduct/queryList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (634, 630, 'Check the details', 'read', '/jxcField/information/1,/jxcProduct/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (636, 630, 'On/Off', 'self', '/jxcProduct/addorUpdateShelf', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (637, 630, 'Delete', 'delete', '/jxcProduct/deleteByIds', NULL, NULL, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (638, 630, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (640, 6, 'Sales order', 'sale', '', NULL, 1, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (641, 640, 'New', 'save', '/jxcSale/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (642, 640, 'Edit', 'update', '/jxcSale/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (643, 640, 'View list', 'index', '/jxcField/queryPageList/5', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (644, 640, 'Check the details', 'read', '/jxcField/information/5,/jxcSale/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (645, 640, 'Delete', 'delete', '/jxcSale/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (646, 640, 'Transfer', 'transfer', '/jxcField/transfer/5', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (647, 640, 'Void', 'setState', '/jxcSale/setStateByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (648, 640, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (650, 6, 'Sales return', 'saleReturn', '', NULL, 1, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (651, 650, 'New', 'save', '/jxcSaleReturn/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (652, 650, 'Edit', 'update', '/jxcSaleReturn/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (653, 650, 'View list', 'index', '/jxcField/queryPageList/6', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (654, 650, 'Check the details', 'read', '/jxcField/information/6,/jxcSaleReturn/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (655, 650, 'Delete', 'delete', '/jxcSaleReturn/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (656, 650, 'Transfer', 'transfer', '/jxcField/transfer/6', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (657, 650, 'Void', 'setState', '/jxcSaleReturn/setStateByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (658, 650, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (670, 6, 'Warehouse management', 'warehouse', '', NULL, 1, 7, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (671, 670, 'New', 'save', '/jxcWarehouse/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (672, 670, 'Edit', 'update', '/jxcWarehouse/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (673, 670, 'View list', 'index', '/jxcWarehouse/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (675, 670, 'Delete', 'delete', '/jxcWarehouse/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (676, 670, 'Disable/enable', 'spst', '/jxcWarehouse/setTrunByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (677, 670, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (680, 6, 'Product Inventory', 'warehouseProduct', '', NULL, 1, 8, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (681, 680, 'View list', 'index', '/jxcWarehouseProduct/queryPageList', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (682, 680, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (690, 6, 'Product warehousing', 'receipt', '', NULL, 1, 9, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (691, 690, 'New', 'save', '/jxcReceipt/addOrUpdate', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (693, 690, 'View list', 'index', '/jxcField/queryPageList/7', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (694, 690, 'Check the details', 'read', '/jxcField/information/7,/jxcReceipt/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (695, 690, 'Void', 'setState', '/jxcReceipt/setStateByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (696, 690, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (700, 6, 'Product out of stock', 'outbound', '', NULL, 1, 10, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (701, 700, 'New', 'save', '/jxcOutbound/addOrUpdate', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (703, 700, 'View list', 'index', '/jxcField/queryPageList/8', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (704, 700, 'Check the details', 'read', '/jxcField/information/8,/jxcOutbound/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (705, 700, 'Void', 'setState', '/jxcOutbound/setStateByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (706, 700, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (710, 6, 'Stock allocation', 'allocation', '', NULL, 1, 11, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (711, 710, 'New', 'save', '/jxcAllocation/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (712, 710, 'Edit', 'update', '/jxcAllocation/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (713, 710, 'View list', 'index', '/jxcField/queryPageList/12', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (714, 710, 'Check the details', 'read', '/jxcField/information/12,/jxcAllocation/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (715, 710, 'Delete', 'delete', '/jxcAllocation/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (716, 710, 'Void', 'setState', '/jxcAllocation/setStateByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (717, 710, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (720, 6, 'Inventory check', 'inventory', '', NULL, 1, 12, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (721, 720, 'New', 'save', '/jxcInventory/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (722, 720, 'Edit', 'update', '/jxcInventory/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (723, 720, 'View list', 'index', '/jxcField/queryPageList/11', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (724, 720, 'Check the details', 'read', '/jxcField/information/11,/jxcInventory/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (725, 720, 'Delete', 'delete', '/jxcInventory/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (726, 720, 'Void', 'setState', '/jxcInventory/setStateByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (727, 720, 'Inventory and storage', 'storage', NULL, NULL, NULL, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (728, 720, 'Inventory void', 'invalid', '', NULL, NULL, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (729, 720, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (730, 6, 'Inbound and outbound details', 'detailed', '', NULL, 1, 13, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (731, 730, 'View list', 'index', '/jxcField/queryPageList/13', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (732, 730, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (740, 6, 'Money back', 'collection', '', NULL, 1, 14, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (741, 740, 'New', 'save', '/jxcCollection/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (742, 740, 'Edit', 'update', '/jxcCollection/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (743, 740, 'View list', 'index', '/jxcField/queryPageList/10', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (744, 740, 'Check the details', 'read', '/jxcField/information/10,/jxcCollection/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (745, 740, 'Delete', 'delete', '/jxcCollection/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (746, 740, 'Transfer', 'transfer', '/jxcField/transfer/10', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (747, 740, 'Void', 'setState', '/jxcCollection/setStateByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (748, 740, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (750, 6, 'Payment', 'payment', '', NULL, 1, 15, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (751, 750, 'New', 'save', '/jxcPayment/addOrUpdate/add', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (752, 750, 'Edit', 'update', '/jxcPayment/addOrUpdate/update', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (753, 750, 'View list', 'index', '/jxcField/queryPageList/9', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (754, 750, 'Check the details', 'read', '/jxcField/information/9,/jxcPayment/queryById/*', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (755, 750, 'Delete', 'delete', '/jxcPayment/deleteByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (756, 750, 'Transfer', 'transfer', '/jxcField/transfer/9', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (757, 750, 'Void', 'setState', '/jxcPayment/setStateByIds', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (758, 750, 'Export', 'excelexport', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (760, 2, 'All', 'jxc', '', NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (761, 760, 'Invoicing and Purchasing Analysis', 'purchasingStatistics', '', NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (762, 761, 'Check', 'read', '/jxcPurchaseStatistics/purchasingStatistics,/jxcProductPurchaseStatistics/productPurchaseStatistics,/jxcProductPurchaseStatistics/purchaseHeadStatistics,/jxcSupplierPurchaseStatistics/supplierPurchaseStatistics', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (763, 760, 'Invoicing and sales analysis', 'saleStatistics', '', NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (764, 763, 'Check', 'read', '/jxcSaleStatistics/saleStatistics,/jxcProductSaleStatistics/productSaleStatistics,/jxcProductSaleStatistics/saleHeadStatistics,/jxcCustomerSaleStatistics/customerSaleStatistics,/jxcCustomerSaleStatistics/customerSaleStatistics', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (765, 760, 'Invoicing product analysis', 'productStatistics', '', NULL, 1, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (766, 765, 'Check', 'read', '/jxcProductStatistics/productStatistics,/jxcProductStatistics/productHeadStatistics', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (800, 5, 'Staff management', 'employee', '', NULL, 1, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (801, 800, 'New', 'save', '/hrmEmployee/addEmployee', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (802, 800, 'Edit', 'update', '/hrmEmployeePost/updatePostInformation,/hrmEmployee/setEduExperience,/hrmEmployee/addExperience,/hrmEmployee/deleteEduExperience,/hrmEmployee/addWorkExperience,/hrmEmployee/setWorkExperience,/hrmEmployee/deleteWorkExperience,/hrmEmployee/addCertificate,/hrmEmployee/setCertificate,/hrmEmployee/deleteCertificate,/hrmEmployee/addTrainingExperience,/hrmEmployee/setTrainingExperience,/hrmEmployee/deleteTrainingExperience,/hrmEmployee/addContacts,/hrmEmployee/setContacts,/hrmEmployee/deleteContacts,/hrmEmployeeContract/addContract,/hrmEmployeeContract/setContract,/hrmEmployeeContract/deleteContract,/SocialSecurity/setSalaryCard,/SocialSecurity/setSocialSecurity,/hrmEmployeeFile/addFile,/hrmEmployeeFile/deleteFile', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (803, 800, 'View list', 'index', '/hrmEmployee/queryPageList', NULL, 3, 3, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (804, 800, 'Check the details', 'read', '/hrmEmployee/queryById,/hrmEmployeePost/postInformation,/hrmEmployee/personalInformation,/hrmEmployeeContract/contractInformation,/hrmEmployee/SocialSecurity/salarySocialSecurityInformation,/hrmEmployeeFile/queryFileNum', NULL, 3, 4, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (805, 800, 'Import', 'excelimport', '/hrmEmployee/uploadExcel', NULL, 3, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (806, 800, 'Export', 'excelexport', '/hrmEmployee/export', NULL, 3, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (807, 800, 'Delete', 'delete', '/hrmEmployee/deleteByIds', NULL, 3, 7, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (808, 800, 'Correction', 'become', '/hrmEmployee/become', NULL, 3, 8, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (809, 800, 'Adjust department/post', 'changePost', '/hrmEmployee/changePost', NULL, 3, 9, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (810, 800, 'Promotion/demotion', 'promotion', '/hrmEmployee/promotion', NULL, 3, 10, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (811, 800, 'Resign', 'leave', '/hrmEmployeePost/addLeaveInformation', NULL, 3, 11, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (812, 800, 'Set up an insurance plan', 'setInsured', '/hrmEmployee/updateInsuranceScheme', NULL, 3, 12, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (813, 800, 'Re-entry', 'againOnboarding', '/hrmEmployee/againOnboarding', NULL, 3, 13, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (814, 800, 'Confirm employment', 'confirmEntry', '/hrmEmployee/confirmEntry', NULL, 3, 13, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (815, 800, 'Give up resignation', 'cancelLevel', '/hrmEmployeePost/deleteLeaveInformation', NULL, 3, 14, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (830, 5, 'Organizational management', 'dept', '', NULL, 1, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (831, 830, 'New', 'save', '/hrmDept/addDept', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (832, 830, 'Edit', 'update', '/hrmDept/setDept', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (833, 830, 'View list', 'index', '', NULL, 3, 3, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (834, 830, 'Check the details', 'read', '/hrmDept/queryById', NULL, 3, 4, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (835, 830, 'Delete', 'delete', '/hrmDept/deleteDeptById', NULL, 3, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (840, 5, 'Payroll Management', 'salary', '', NULL, 1, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (841, 840, 'Payroll Maintenance', 'manage', '/hrmSalaryMonthRecord/computeSalaryData,/hrmSalaryMonthRecord/updateSalary,/hrmSalaryMonthRecord/submitExamine,/hrmSalaryMonthRecord/addNextMonthSalary', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (842, 840, 'View salary list', 'index', '/hrmSalaryOption/querySalaryOptionDetail', NULL, 3, 2, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (843, 840, 'View salary history', 'history', '/hrmSalaryHistoryRecord/queryHistorySalaryList', NULL, 3, 3, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (844, 840, 'Pay stubs', 'sendSlip', '/hrmSalarySlipRecord/sendSalarySlip', NULL, 3, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (845, 840, 'View Release History', 'queryRecord', '', NULL, 3, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (846, 840, 'View Salary Profile', 'queryArchives', '/hrmSalaryArchives/querySalaryArchivesList', NULL, 3, 6, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (847, 840, 'Maintain payroll files', 'updateArchives', '/hrmSalaryArchives/setFixSalaryRecord,/hrmSalaryArchives/setChangeSalaryRecord,/deleteChangeSalary/setChangeSalaryRecord,/deleteChangeSalary/batchChangeSalaryRecord,/deleteChangeSalary/exportFixSalaryRecord', NULL, 3, 7, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (850, 5, 'Social Security Management', 'insurance', '', NULL, 1, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (851, 850, 'Maintain social security', 'manage', '/hrmInsuranceMonthRecord/computeInsuranceData,/hrmInsuranceMonthEmpRecord/stop,/hrmInsuranceMonthEmpRecord/updateInsuranceProject', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (852, 850, 'View Social Security', 'read', '/hrmInsuranceMonthRecord/queryInsuranceRecordList', NULL, 3, 2, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (860, 5, 'Recruitment management', 'recruit', '', NULL, 1, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (861, 860, 'New candidate', 'save', '/hrmRecruitCandidate/addCandidate', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (862, 860, 'View candidates', 'read', '/hrmRecruitCandidate/queryPageList,/hrmRecruitCandidate/queryById', NULL, 3, 2, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (863, 860, 'Maintenance candidates', 'manage', '/hrmRecruitCandidate/setCandidate,/hrmRecruitCandidate/updateCandidateStatus,/hrmRecruitCandidate/updateCandidatePost,/hrmRecruitCandidate/updateCandidateRecruitChannel,/hrmRecruitCandidate/eliminateCandidate', NULL, 3, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (864, 860, 'Delete candidate', 'delete', '/hrmRecruitCandidate/deleteByIds,/hrmRecruitCandidate/deleteById', NULL, 3, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (865, 860, 'Create a new job', 'savePost', '/hrmRecruitPost/addRecruitPost', NULL, 3, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (866, 860, 'Edit job postings', 'updatePost', '/hrmRecruitPost/setRecruitPost', NULL, 3, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (867, 860, 'View job openings', 'readPost', '/hrmRecruitPost/queryRecruitPostPageList,/hrmRecruitPost/queryById', NULL, 3, 7, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (868, 860, 'Disable/enable job postings', 'updatePostStatus', '/hrmRecruitPost/updateRecruitPostStatus', NULL, 3, 8, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (880, 5, 'Performance management', 'appraisal', '', NULL, 1, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (881, 880, 'New performance', 'save', '/hrmRecruitPost/addAppraisal', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (882, 880, 'Editorial performance', 'update', '/hrmRecruitPost/setAppraisal', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (883, 880, 'View performance', 'read', '', NULL, 3, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (884, 880, 'Delete performance', 'delete', '/hrmRecruitPost/delete', NULL, 3, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (885, 880, 'Terminate performance', 'stop', '/hrmRecruitPoststopAppraisal', NULL, 3, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (886, 880, 'View employee performance', 'readEmp', '', NULL, 3, 3, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (890, 5, 'Attendance management', 'attendance', '', NULL, 1, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (891, 890, 'View punch records', 'readClock', '/hrmRecruitPost/addAppraisal', NULL, 3, 1, 1, 'label-92');
INSERT INTO `wk_admin_menu` VALUES (892, 890, 'Export punch records', 'excelexport', '/hrmAttendanceClock/excelExport', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (900, 3, 'Human resource Management', 'hrm', NULL, NULL, 1, 10, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (901, 900, 'Custom Field Settings', 'field', '/hrmConfig/queryFields', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (902, 900, 'Salary settings', 'salary', '/hrmSalaryGroup/querySalaryGroupPageList', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (903, 900, 'Social Security Settings', 'insurance', '', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (904, 900, 'Performance settings', 'appraisal', '', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (905, 900, 'Business parameter settings', 'params', '/hrmConfig/queryRecruitChannelList', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (906, 900, 'Employee Profile Settings', 'archives', '', NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (920, 3, 'Invoicing management', 'jxc', NULL, NULL, 1, 11, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (921, 920, 'Custom Field Settings', 'field', '/jxcField/queryFields', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (922, 920, 'Business parameter settings', 'params', '/jxcProductType/queryJxcProductTyp,/jxcNumberSetting/queryNumberSetting', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (923, 3, 'Initialization', 'init', NULL, NULL, 1, 12, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (924, 923, 'Initialization management', 'initData', '/adminConfig/moduleInitData', NULL, 3, 0, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (926, 180, 'Campaign Form Settings', 'activityForm', '/crmMarketingForm/page', NULL, 3, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (927, 301, 'Manage participant permissions', 'manageTaskOwnerUser', '', NULL, 3, 29, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (928, 440, 'Import', 'excelimport', '/crmInstrument/importRecordList', NULL, 3, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (929, 440, 'Export', 'excelexport', '/crmInstrument/exportRecordList', NULL, 3, 6, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (933, 11, 'Editorial team members', 'teamsave', '/crmContacts/addMembers,/crmContacts/updateMembers,/crmContacts/exitTeam', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (934, 14, 'Editorial team members', 'teamsave', '/crmReceivables/addMembers,/crmReceivables/updateMembers,/crmReceivables/exitTeam', NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (935, 166, 'View role permissions', 'read', NULL, NULL, 3, 8, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (936, 1, 'Repayment plan', 'receivablesPlan', NULL, NULL, 1, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (937, 936, 'New', 'save', NULL, NULL, 3, 1, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (938, 936, 'Edit', 'update', NULL, NULL, 3, 2, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (939, 936, 'View list', 'index', NULL, NULL, 3, 3, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (940, 936, 'Check the details', 'read', NULL, NULL, 3, 4, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (941, 936, 'Delete', 'delete', NULL, NULL, 3, 5, 1, NULL);
INSERT INTO `wk_admin_menu` VALUES (942, 936, 'Export', 'excelexport', NULL, NULL, 3, 6, 1, NULL);

-- ----------------------------
-- Table structure for wk_admin_message
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_message`;
CREATE TABLE `wk_admin_message`  (
  `message_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'Message id',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Message title',
  `content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Content',
  `label` int(0) NULL DEFAULT NULL COMMENT 'Message Category 1 Task 2 Log 3 oa Approval 4 Announcement 5 Schedule 6 CRM Message 7 Knowledge Base 8 Human Resources',
  `type` int(0) NULL DEFAULT NULL COMMENT 'Message type See AdminMessageEnum for details',
  `type_id` int(0) NULL DEFAULT NULL COMMENT 'Link ID',
  `create_user` bigint(0) NOT NULL COMMENT 'Message creator 0 is system',
  `recipient_user` bigint(0) NOT NULL COMMENT 'Receiver',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `is_read` int(0) NULL DEFAULT 0 COMMENT 'Has it been read 0 Unread 1 Read',
  `read_time` datetime(0) NULL DEFAULT NULL COMMENT 'Elapsed time',
  PRIMARY KEY (`message_id`) USING BTREE,
  INDEX `recipient_user`(`recipient_user`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'System message table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_message
-- ----------------------------

-- ----------------------------
-- Table structure for wk_admin_model_sort
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_model_sort`;
CREATE TABLE `wk_admin_model_sort`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NOT NULL COMMENT 'Navigation Type 1Head Navigation 2Customer Management Left Navigation',
  `model` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Modules 1 Dashboard 2 To Do 3 Leads 4 Customers 5 Contacts 6 Opportunities 7 Contracts 8 Payments 9 Invoices 10 Returns 11 Products 12 Marketing Activities',
  `sort` int(0) NOT NULL COMMENT 'Sort',
  `is_hidden` int(0) NULL DEFAULT 0 COMMENT 'Whether to hide 0 not to hide 1 to hide',
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Customer Management Navigation Bar Sort Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_model_sort
-- ----------------------------

-- ----------------------------
-- Table structure for wk_admin_official_img
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_official_img`;
CREATE TABLE `wk_admin_official_img`  (
  `official_img_id` int(0) NOT NULL AUTO_INCREMENT,
  `size` bigint(0) NULL DEFAULT NULL COMMENT 'Attachment size (bytes)',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'File path',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'File path',
  `type` int(0) NULL DEFAULT NULL COMMENT '1. Official website settings 2. Business card poster',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tactic` int(0) NULL DEFAULT NULL COMMENT '0',
  PRIMARY KEY (`official_img_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Official website pictures' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_official_img
-- ----------------------------

-- ----------------------------
-- Table structure for wk_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_role`;
CREATE TABLE `wk_admin_role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Name',
  `role_type` int(0) NULL DEFAULT NULL COMMENT '0. Custom role 1. Management role 2. Customer management role 3. Personnel role 4. Financial role 5. Project role 8. Project custom role',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `status` int(0) NULL DEFAULT 1 COMMENT '1 enable 0 disable',
  `data_type` int(0) NOT NULL DEFAULT 5 COMMENT 'Data rights 1. Me, 2. Me and my subordinates, 3. My department, 4. My department and its subordinate departments, 5. All',
  `is_hidden` int(0) NOT NULL DEFAULT 1 COMMENT '0 hidden 1 not hidden',
  `label` int(0) NULL DEFAULT NULL COMMENT '1 System Project Administrator role 2 Project Management role 3 Project Editor role 4 Project Read-Only role',
  PRIMARY KEY (`role_id`) USING BTREE,
  INDEX `role_type`(`role_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 180177 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Role table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_role
-- ----------------------------
INSERT INTO `wk_admin_role` VALUES (180162, 'Super administrator', 1, 'admin', 1, 5, 1, 5);
INSERT INTO `wk_admin_role` VALUES (180163, 'System Settings Administrator', 1, NULL, 1, 2, 1, 6);
INSERT INTO `wk_admin_role` VALUES (180164, 'Department and employee administrators', 1, NULL, 1, 5, 1, 7);
INSERT INTO `wk_admin_role` VALUES (180165, 'Approval Flow Manager', 1, NULL, 1, 5, 1, 8);
INSERT INTO `wk_admin_role` VALUES (180166, 'Workbench administrator', 1, NULL, 1, 5, 1, 9);
INSERT INTO `wk_admin_role` VALUES (180167, 'Customer administrator', 1, NULL, 1, 5, 1, 10);
INSERT INTO `wk_admin_role` VALUES (180168, 'Announcement administrator', 7, NULL, 1, 5, 1, 11);
INSERT INTO `wk_admin_role` VALUES (180169, 'Sales manager role', 2, NULL, 1, 5, 1, NULL);
INSERT INTO `wk_admin_role` VALUES (180170, 'Salesperson role', 2, NULL, 1, 1, 1, NULL);
INSERT INTO `wk_admin_role` VALUES (180171, 'Project administrator', 8, 'project', 1, 5, 1, 1);
INSERT INTO `wk_admin_role` VALUES (180172, 'Manage', 5, 'System default permissions, including all permissions of the project', 1, 5, 0, 2);
INSERT INTO `wk_admin_role` VALUES (180173, 'Edit', 5, 'Permissions that members have by default when they initially join', 1, 5, 1, 3);
INSERT INTO `wk_admin_role` VALUES (180174, 'Read only', 5, 'Project read-only role', 1, 1, 1, 4);
INSERT INTO `wk_admin_role` VALUES (180175, 'Superior role', 9, NULL, 1, 2, 1, 91);

-- ----------------------------
-- Table structure for wk_admin_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_role_auth`;
CREATE TABLE `wk_admin_role_auth`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_id` int(0) NOT NULL COMMENT 'Role id',
  `menu_id` int(0) NOT NULL COMMENT 'Menu ID',
  `auth_role_id` int(0) NULL DEFAULT NULL COMMENT 'Character IDs that can be queried',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_type`(`auth_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5449 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Role table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_role_auth
-- ----------------------------

-- ----------------------------
-- Table structure for wk_admin_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_role_menu`;
CREATE TABLE `wk_admin_role_menu`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_id` int(0) NOT NULL COMMENT 'Role id',
  `menu_id` int(0) NOT NULL COMMENT 'Menu ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `menu_id`(`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2300834 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Role menu correspondence table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_role_menu
-- ----------------------------
INSERT INTO `wk_admin_role_menu` VALUES (2300572, 180163, 3);
INSERT INTO `wk_admin_role_menu` VALUES (2300573, 180163, 160);
INSERT INTO `wk_admin_role_menu` VALUES (2300574, 180163, 161);
INSERT INTO `wk_admin_role_menu` VALUES (2300575, 180163, 162);
INSERT INTO `wk_admin_role_menu` VALUES (2300576, 180163, 163);
INSERT INTO `wk_admin_role_menu` VALUES (2300577, 180163, 164);
INSERT INTO `wk_admin_role_menu` VALUES (2300578, 180163, 165);
INSERT INTO `wk_admin_role_menu` VALUES (2300579, 180163, 166);
INSERT INTO `wk_admin_role_menu` VALUES (2300580, 180163, 167);
INSERT INTO `wk_admin_role_menu` VALUES (2300581, 180163, 168);
INSERT INTO `wk_admin_role_menu` VALUES (2300582, 180163, 169);
INSERT INTO `wk_admin_role_menu` VALUES (2300583, 180163, 170);
INSERT INTO `wk_admin_role_menu` VALUES (2300584, 180163, 171);
INSERT INTO `wk_admin_role_menu` VALUES (2300585, 180163, 172);
INSERT INTO `wk_admin_role_menu` VALUES (2300586, 180163, 173);
INSERT INTO `wk_admin_role_menu` VALUES (2300587, 180163, 174);
INSERT INTO `wk_admin_role_menu` VALUES (2300588, 180163, 175);
INSERT INTO `wk_admin_role_menu` VALUES (2300589, 180163, 176);
INSERT INTO `wk_admin_role_menu` VALUES (2300590, 180163, 177);
INSERT INTO `wk_admin_role_menu` VALUES (2300591, 180163, 178);
INSERT INTO `wk_admin_role_menu` VALUES (2300592, 180163, 179);
INSERT INTO `wk_admin_role_menu` VALUES (2300593, 180163, 180);
INSERT INTO `wk_admin_role_menu` VALUES (2300594, 180163, 181);
INSERT INTO `wk_admin_role_menu` VALUES (2300595, 180163, 182);
INSERT INTO `wk_admin_role_menu` VALUES (2300596, 180163, 183);
INSERT INTO `wk_admin_role_menu` VALUES (2300597, 180163, 184);
INSERT INTO `wk_admin_role_menu` VALUES (2300598, 180163, 185);
INSERT INTO `wk_admin_role_menu` VALUES (2300599, 180163, 186);
INSERT INTO `wk_admin_role_menu` VALUES (2300600, 180164, 166);
INSERT INTO `wk_admin_role_menu` VALUES (2300601, 180164, 167);
INSERT INTO `wk_admin_role_menu` VALUES (2300602, 180164, 168);
INSERT INTO `wk_admin_role_menu` VALUES (2300603, 180164, 169);
INSERT INTO `wk_admin_role_menu` VALUES (2300604, 180164, 170);
INSERT INTO `wk_admin_role_menu` VALUES (2300605, 180164, 171);
INSERT INTO `wk_admin_role_menu` VALUES (2300606, 180164, 172);
INSERT INTO `wk_admin_role_menu` VALUES (2300607, 180164, 173);
INSERT INTO `wk_admin_role_menu` VALUES (2300608, 180165, 178);
INSERT INTO `wk_admin_role_menu` VALUES (2300609, 180165, 179);
INSERT INTO `wk_admin_role_menu` VALUES (2300610, 180166, 176);
INSERT INTO `wk_admin_role_menu` VALUES (2300611, 180166, 177);
INSERT INTO `wk_admin_role_menu` VALUES (2300612, 180167, 180);
INSERT INTO `wk_admin_role_menu` VALUES (2300613, 180167, 181);
INSERT INTO `wk_admin_role_menu` VALUES (2300614, 180167, 182);
INSERT INTO `wk_admin_role_menu` VALUES (2300615, 180167, 183);
INSERT INTO `wk_admin_role_menu` VALUES (2300616, 180167, 184);
INSERT INTO `wk_admin_role_menu` VALUES (2300617, 180168, 187);
INSERT INTO `wk_admin_role_menu` VALUES (2300618, 180168, 188);
INSERT INTO `wk_admin_role_menu` VALUES (2300619, 180168, 189);
INSERT INTO `wk_admin_role_menu` VALUES (2300620, 180168, 190);
INSERT INTO `wk_admin_role_menu` VALUES (2300621, 180169, 9);
INSERT INTO `wk_admin_role_menu` VALUES (2300622, 180169, 10);
INSERT INTO `wk_admin_role_menu` VALUES (2300623, 180169, 11);
INSERT INTO `wk_admin_role_menu` VALUES (2300624, 180169, 12);
INSERT INTO `wk_admin_role_menu` VALUES (2300625, 180169, 13);
INSERT INTO `wk_admin_role_menu` VALUES (2300626, 180169, 14);
INSERT INTO `wk_admin_role_menu` VALUES (2300627, 180169, 17);
INSERT INTO `wk_admin_role_menu` VALUES (2300628, 180169, 18);
INSERT INTO `wk_admin_role_menu` VALUES (2300629, 180169, 19);
INSERT INTO `wk_admin_role_menu` VALUES (2300630, 180169, 20);
INSERT INTO `wk_admin_role_menu` VALUES (2300631, 180169, 21);
INSERT INTO `wk_admin_role_menu` VALUES (2300632, 180169, 22);
INSERT INTO `wk_admin_role_menu` VALUES (2300633, 180169, 23);
INSERT INTO `wk_admin_role_menu` VALUES (2300634, 180169, 24);
INSERT INTO `wk_admin_role_menu` VALUES (2300635, 180169, 25);
INSERT INTO `wk_admin_role_menu` VALUES (2300636, 180169, 26);
INSERT INTO `wk_admin_role_menu` VALUES (2300637, 180169, 27);
INSERT INTO `wk_admin_role_menu` VALUES (2300638, 180169, 28);
INSERT INTO `wk_admin_role_menu` VALUES (2300639, 180169, 29);
INSERT INTO `wk_admin_role_menu` VALUES (2300640, 180169, 30);
INSERT INTO `wk_admin_role_menu` VALUES (2300641, 180169, 31);
INSERT INTO `wk_admin_role_menu` VALUES (2300642, 180169, 32);
INSERT INTO `wk_admin_role_menu` VALUES (2300643, 180169, 33);
INSERT INTO `wk_admin_role_menu` VALUES (2300644, 180169, 34);
INSERT INTO `wk_admin_role_menu` VALUES (2300645, 180169, 35);
INSERT INTO `wk_admin_role_menu` VALUES (2300646, 180169, 36);
INSERT INTO `wk_admin_role_menu` VALUES (2300647, 180169, 40);
INSERT INTO `wk_admin_role_menu` VALUES (2300648, 180169, 41);
INSERT INTO `wk_admin_role_menu` VALUES (2300649, 180169, 42);
INSERT INTO `wk_admin_role_menu` VALUES (2300650, 180169, 43);
INSERT INTO `wk_admin_role_menu` VALUES (2300651, 180169, 44);
INSERT INTO `wk_admin_role_menu` VALUES (2300652, 180169, 45);
INSERT INTO `wk_admin_role_menu` VALUES (2300653, 180169, 46);
INSERT INTO `wk_admin_role_menu` VALUES (2300654, 180169, 47);
INSERT INTO `wk_admin_role_menu` VALUES (2300655, 180169, 48);
INSERT INTO `wk_admin_role_menu` VALUES (2300656, 180169, 49);
INSERT INTO `wk_admin_role_menu` VALUES (2300657, 180169, 50);
INSERT INTO `wk_admin_role_menu` VALUES (2300658, 180169, 51);
INSERT INTO `wk_admin_role_menu` VALUES (2300659, 180169, 52);
INSERT INTO `wk_admin_role_menu` VALUES (2300660, 180169, 53);
INSERT INTO `wk_admin_role_menu` VALUES (2300661, 180169, 54);
INSERT INTO `wk_admin_role_menu` VALUES (2300662, 180169, 55);
INSERT INTO `wk_admin_role_menu` VALUES (2300663, 180169, 56);
INSERT INTO `wk_admin_role_menu` VALUES (2300664, 180169, 57);
INSERT INTO `wk_admin_role_menu` VALUES (2300665, 180169, 58);
INSERT INTO `wk_admin_role_menu` VALUES (2300666, 180169, 59);
INSERT INTO `wk_admin_role_menu` VALUES (2300667, 180169, 60);
INSERT INTO `wk_admin_role_menu` VALUES (2300668, 180169, 61);
INSERT INTO `wk_admin_role_menu` VALUES (2300669, 180169, 62);
INSERT INTO `wk_admin_role_menu` VALUES (2300670, 180169, 63);
INSERT INTO `wk_admin_role_menu` VALUES (2300671, 180169, 64);
INSERT INTO `wk_admin_role_menu` VALUES (2300672, 180169, 67);
INSERT INTO `wk_admin_role_menu` VALUES (2300673, 180169, 68);
INSERT INTO `wk_admin_role_menu` VALUES (2300674, 180169, 97);
INSERT INTO `wk_admin_role_menu` VALUES (2300675, 180169, 98);
INSERT INTO `wk_admin_role_menu` VALUES (2300676, 180169, 99);
INSERT INTO `wk_admin_role_menu` VALUES (2300677, 180169, 101);
INSERT INTO `wk_admin_role_menu` VALUES (2300678, 180169, 102);
INSERT INTO `wk_admin_role_menu` VALUES (2300679, 180169, 103);
INSERT INTO `wk_admin_role_menu` VALUES (2300680, 180169, 104);
INSERT INTO `wk_admin_role_menu` VALUES (2300681, 180169, 106);
INSERT INTO `wk_admin_role_menu` VALUES (2300682, 180169, 107);
INSERT INTO `wk_admin_role_menu` VALUES (2300683, 180169, 108);
INSERT INTO `wk_admin_role_menu` VALUES (2300684, 180169, 117);
INSERT INTO `wk_admin_role_menu` VALUES (2300685, 180169, 118);
INSERT INTO `wk_admin_role_menu` VALUES (2300686, 180169, 123);
INSERT INTO `wk_admin_role_menu` VALUES (2300687, 180169, 124);
INSERT INTO `wk_admin_role_menu` VALUES (2300688, 180169, 125);
INSERT INTO `wk_admin_role_menu` VALUES (2300689, 180169, 126);
INSERT INTO `wk_admin_role_menu` VALUES (2300690, 180169, 191);
INSERT INTO `wk_admin_role_menu` VALUES (2300691, 180169, 192);
INSERT INTO `wk_admin_role_menu` VALUES (2300692, 180170, 14);
INSERT INTO `wk_admin_role_menu` VALUES (2300693, 180170, 17);
INSERT INTO `wk_admin_role_menu` VALUES (2300694, 180170, 18);
INSERT INTO `wk_admin_role_menu` VALUES (2300695, 180170, 19);
INSERT INTO `wk_admin_role_menu` VALUES (2300696, 180170, 20);
INSERT INTO `wk_admin_role_menu` VALUES (2300697, 180170, 21);
INSERT INTO `wk_admin_role_menu` VALUES (2300698, 180170, 25);
INSERT INTO `wk_admin_role_menu` VALUES (2300699, 180170, 26);
INSERT INTO `wk_admin_role_menu` VALUES (2300700, 180170, 27);
INSERT INTO `wk_admin_role_menu` VALUES (2300701, 180170, 28);
INSERT INTO `wk_admin_role_menu` VALUES (2300702, 180170, 29);
INSERT INTO `wk_admin_role_menu` VALUES (2300703, 180170, 30);
INSERT INTO `wk_admin_role_menu` VALUES (2300704, 180170, 34);
INSERT INTO `wk_admin_role_menu` VALUES (2300705, 180170, 35);
INSERT INTO `wk_admin_role_menu` VALUES (2300706, 180170, 36);
INSERT INTO `wk_admin_role_menu` VALUES (2300707, 180170, 40);
INSERT INTO `wk_admin_role_menu` VALUES (2300708, 180170, 41);
INSERT INTO `wk_admin_role_menu` VALUES (2300709, 180170, 42);
INSERT INTO `wk_admin_role_menu` VALUES (2300710, 180170, 43);
INSERT INTO `wk_admin_role_menu` VALUES (2300711, 180170, 44);
INSERT INTO `wk_admin_role_menu` VALUES (2300712, 180170, 45);
INSERT INTO `wk_admin_role_menu` VALUES (2300713, 180170, 46);
INSERT INTO `wk_admin_role_menu` VALUES (2300714, 180170, 47);
INSERT INTO `wk_admin_role_menu` VALUES (2300715, 180170, 48);
INSERT INTO `wk_admin_role_menu` VALUES (2300716, 180170, 49);
INSERT INTO `wk_admin_role_menu` VALUES (2300717, 180170, 50);
INSERT INTO `wk_admin_role_menu` VALUES (2300718, 180170, 52);
INSERT INTO `wk_admin_role_menu` VALUES (2300719, 180170, 53);
INSERT INTO `wk_admin_role_menu` VALUES (2300720, 180170, 54);
INSERT INTO `wk_admin_role_menu` VALUES (2300721, 180170, 55);
INSERT INTO `wk_admin_role_menu` VALUES (2300722, 180170, 56);
INSERT INTO `wk_admin_role_menu` VALUES (2300723, 180170, 57);
INSERT INTO `wk_admin_role_menu` VALUES (2300724, 180170, 59);
INSERT INTO `wk_admin_role_menu` VALUES (2300725, 180170, 60);
INSERT INTO `wk_admin_role_menu` VALUES (2300726, 180170, 61);
INSERT INTO `wk_admin_role_menu` VALUES (2300727, 180170, 62);
INSERT INTO `wk_admin_role_menu` VALUES (2300728, 180170, 63);
INSERT INTO `wk_admin_role_menu` VALUES (2300729, 180170, 64);
INSERT INTO `wk_admin_role_menu` VALUES (2300730, 180170, 67);
INSERT INTO `wk_admin_role_menu` VALUES (2300731, 180170, 68);
INSERT INTO `wk_admin_role_menu` VALUES (2300732, 180170, 97);
INSERT INTO `wk_admin_role_menu` VALUES (2300733, 180170, 98);
INSERT INTO `wk_admin_role_menu` VALUES (2300734, 180170, 99);
INSERT INTO `wk_admin_role_menu` VALUES (2300735, 180170, 101);
INSERT INTO `wk_admin_role_menu` VALUES (2300736, 180170, 102);
INSERT INTO `wk_admin_role_menu` VALUES (2300737, 180170, 103);
INSERT INTO `wk_admin_role_menu` VALUES (2300738, 180170, 104);
INSERT INTO `wk_admin_role_menu` VALUES (2300739, 180170, 106);
INSERT INTO `wk_admin_role_menu` VALUES (2300740, 180170, 108);
INSERT INTO `wk_admin_role_menu` VALUES (2300741, 180170, 117);
INSERT INTO `wk_admin_role_menu` VALUES (2300742, 180170, 118);
INSERT INTO `wk_admin_role_menu` VALUES (2300743, 180170, 123);
INSERT INTO `wk_admin_role_menu` VALUES (2300744, 180170, 124);
INSERT INTO `wk_admin_role_menu` VALUES (2300745, 180170, 125);
INSERT INTO `wk_admin_role_menu` VALUES (2300746, 180170, 126);
INSERT INTO `wk_admin_role_menu` VALUES (2300747, 180173, 316);
INSERT INTO `wk_admin_role_menu` VALUES (2300748, 180173, 317);
INSERT INTO `wk_admin_role_menu` VALUES (2300749, 180173, 318);
INSERT INTO `wk_admin_role_menu` VALUES (2300750, 180173, 319);
INSERT INTO `wk_admin_role_menu` VALUES (2300751, 180173, 320);
INSERT INTO `wk_admin_role_menu` VALUES (2300752, 180173, 321);
INSERT INTO `wk_admin_role_menu` VALUES (2300753, 180173, 322);
INSERT INTO `wk_admin_role_menu` VALUES (2300754, 180173, 323);
INSERT INTO `wk_admin_role_menu` VALUES (2300755, 180173, 324);
INSERT INTO `wk_admin_role_menu` VALUES (2300756, 180173, 325);
INSERT INTO `wk_admin_role_menu` VALUES (2300757, 180173, 326);
INSERT INTO `wk_admin_role_menu` VALUES (2300758, 180173, 327);
INSERT INTO `wk_admin_role_menu` VALUES (2300759, 180173, 329);
INSERT INTO `wk_admin_role_menu` VALUES (2300760, 180173, 330);
INSERT INTO `wk_admin_role_menu` VALUES (2300761, 180173, 331);
INSERT INTO `wk_admin_role_menu` VALUES (2300762, 180173, 332);
INSERT INTO `wk_admin_role_menu` VALUES (2300763, 180173, 333);
INSERT INTO `wk_admin_role_menu` VALUES (2300764, 180173, 334);
INSERT INTO `wk_admin_role_menu` VALUES (2300765, 180173, 335);
INSERT INTO `wk_admin_role_menu` VALUES (2300766, 180173, 337);
INSERT INTO `wk_admin_role_menu` VALUES (2300767, 180171, 149);
INSERT INTO `wk_admin_role_menu` VALUES (2300768, 180171, 152);
INSERT INTO `wk_admin_role_menu` VALUES (2300769, 180171, 153);
INSERT INTO `wk_admin_role_menu` VALUES (2300770, 180169, 2);
INSERT INTO `wk_admin_role_menu` VALUES (2300771, 180169, 15);
INSERT INTO `wk_admin_role_menu` VALUES (2300772, 180169, 65);
INSERT INTO `wk_admin_role_menu` VALUES (2300773, 180169, 66);
INSERT INTO `wk_admin_role_menu` VALUES (2300774, 180169, 69);
INSERT INTO `wk_admin_role_menu` VALUES (2300775, 180169, 70);
INSERT INTO `wk_admin_role_menu` VALUES (2300776, 180169, 109);
INSERT INTO `wk_admin_role_menu` VALUES (2300777, 180169, 110);
INSERT INTO `wk_admin_role_menu` VALUES (2300778, 180169, 193);
INSERT INTO `wk_admin_role_menu` VALUES (2300779, 180169, 194);
INSERT INTO `wk_admin_role_menu` VALUES (2300780, 180169, 195);
INSERT INTO `wk_admin_role_menu` VALUES (2300781, 180169, 211);
INSERT INTO `wk_admin_role_menu` VALUES (2300782, 180169, 400);
INSERT INTO `wk_admin_role_menu` VALUES (2300783, 180169, 401);
INSERT INTO `wk_admin_role_menu` VALUES (2300784, 180169, 402);
INSERT INTO `wk_admin_role_menu` VALUES (2300785, 180169, 403);
INSERT INTO `wk_admin_role_menu` VALUES (2300786, 180169, 404);
INSERT INTO `wk_admin_role_menu` VALUES (2300787, 180169, 405);
INSERT INTO `wk_admin_role_menu` VALUES (2300788, 180169, 420);
INSERT INTO `wk_admin_role_menu` VALUES (2300789, 180169, 421);
INSERT INTO `wk_admin_role_menu` VALUES (2300790, 180169, 422);
INSERT INTO `wk_admin_role_menu` VALUES (2300791, 180169, 423);
INSERT INTO `wk_admin_role_menu` VALUES (2300792, 180169, 424);
INSERT INTO `wk_admin_role_menu` VALUES (2300793, 180169, 425);
INSERT INTO `wk_admin_role_menu` VALUES (2300794, 180169, 426);
INSERT INTO `wk_admin_role_menu` VALUES (2300795, 180169, 427);
INSERT INTO `wk_admin_role_menu` VALUES (2300796, 180169, 428);
INSERT INTO `wk_admin_role_menu` VALUES (2300797, 180169, 440);
INSERT INTO `wk_admin_role_menu` VALUES (2300798, 180169, 441);
INSERT INTO `wk_admin_role_menu` VALUES (2300799, 180169, 442);
INSERT INTO `wk_admin_role_menu` VALUES (2300800, 180169, 443);
INSERT INTO `wk_admin_role_menu` VALUES (2300801, 180169, 444);
INSERT INTO `wk_admin_role_menu` VALUES (2300802, 180170, 400);
INSERT INTO `wk_admin_role_menu` VALUES (2300803, 180170, 401);
INSERT INTO `wk_admin_role_menu` VALUES (2300804, 180170, 402);
INSERT INTO `wk_admin_role_menu` VALUES (2300805, 180170, 403);
INSERT INTO `wk_admin_role_menu` VALUES (2300806, 180170, 404);
INSERT INTO `wk_admin_role_menu` VALUES (2300807, 180170, 405);
INSERT INTO `wk_admin_role_menu` VALUES (2300808, 180170, 420);
INSERT INTO `wk_admin_role_menu` VALUES (2300809, 180170, 421);
INSERT INTO `wk_admin_role_menu` VALUES (2300810, 180170, 422);
INSERT INTO `wk_admin_role_menu` VALUES (2300811, 180170, 423);
INSERT INTO `wk_admin_role_menu` VALUES (2300812, 180170, 424);
INSERT INTO `wk_admin_role_menu` VALUES (2300813, 180170, 425);
INSERT INTO `wk_admin_role_menu` VALUES (2300814, 180170, 426);
INSERT INTO `wk_admin_role_menu` VALUES (2300815, 180170, 427);
INSERT INTO `wk_admin_role_menu` VALUES (2300816, 180170, 428);
INSERT INTO `wk_admin_role_menu` VALUES (2300817, 180170, 440);
INSERT INTO `wk_admin_role_menu` VALUES (2300818, 180170, 441);
INSERT INTO `wk_admin_role_menu` VALUES (2300819, 180170, 442);
INSERT INTO `wk_admin_role_menu` VALUES (2300820, 180170, 443);
INSERT INTO `wk_admin_role_menu` VALUES (2300821, 180170, 444);
INSERT INTO `wk_admin_role_menu` VALUES (2300822, 180176, 803);
INSERT INTO `wk_admin_role_menu` VALUES (2300823, 180176, 804);
INSERT INTO `wk_admin_role_menu` VALUES (2300824, 180176, 833);
INSERT INTO `wk_admin_role_menu` VALUES (2300825, 180176, 834);
INSERT INTO `wk_admin_role_menu` VALUES (2300826, 180176, 842);
INSERT INTO `wk_admin_role_menu` VALUES (2300827, 180176, 843);
INSERT INTO `wk_admin_role_menu` VALUES (2300828, 180176, 846);
INSERT INTO `wk_admin_role_menu` VALUES (2300829, 180176, 852);
INSERT INTO `wk_admin_role_menu` VALUES (2300830, 180176, 862);
INSERT INTO `wk_admin_role_menu` VALUES (2300831, 180176, 867);
INSERT INTO `wk_admin_role_menu` VALUES (2300832, 180176, 886);
INSERT INTO `wk_admin_role_menu` VALUES (2300833, 180176, 891);

-- ----------------------------
-- Table structure for wk_admin_system_log
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_system_log`;
CREATE TABLE `wk_admin_system_log`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `create_user_id` int(0) NOT NULL COMMENT 'Operator id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP address',
  `types` int(0) NULL DEFAULT NULL COMMENT 'Module 1Enterprise Homepage 2Application Management 3Employee and Department Management 4Business Card Mini Program Management 5Role Permission Management 6Approval Flow (Contract/Collection) 7Approval Flow (Office) 8Project Management 9Customer Management 10System Log Management 11Other Settings',
  `behavior` int(0) NULL DEFAULT NULL COMMENT 'Behavior',
  `object` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Operation object',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Operation details',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'System log table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_system_log
-- ----------------------------

-- ----------------------------
-- Table structure for wk_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_user`;
CREATE TABLE `wk_admin_user`  (
  `user_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Username',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Password',
  `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Safety sign',
  `img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Avatar',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `realname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Actual name',
  `num` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Staff code',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Phone number',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Mail',
  `sex` int(0) NULL DEFAULT NULL COMMENT '0 not selected 1 male 2 female',
  `dept_id` int(0) NULL DEFAULT NULL COMMENT 'Department',
  `post` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Post',
  `status` int(0) NULL DEFAULT 2 COMMENT 'Status, 0 disabled, 1 normal, 2 inactive',
  `parent_id` bigint(0) NULL DEFAULT 0 COMMENT 'Direct superior ID',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT 'Last login time',
  `last_login_ip` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'The last login IP Note that it is compatible with IPV6',
  `old_user_id` bigint(0) NULL DEFAULT NULL,
  `is_del` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to delete 0 Not deleted 1 Deleted',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `parent_id`(`parent_id`) USING BTREE,
  INDEX `dept_id`(`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'User table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_user
-- ----------------------------

-- ----------------------------
-- Table structure for wk_admin_user_config
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_user_config`;
CREATE TABLE `wk_admin_user_config`  (
  `setting_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL,
  `status` int(0) NOT NULL DEFAULT 0 COMMENT 'Status, 0: not enabled 1: enabled',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Set name',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Value',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Describe',
  PRIMARY KEY (`setting_id`) USING BTREE,
  INDEX `name`(`name`, `user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 114574 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'User configuration table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_user_config
-- ----------------------------
INSERT INTO `wk_admin_user_config` VALUES (114563, 14773, 1, 'ActivityPhrase', 'No one answered the phone', 'Follow-up Recording Phrases');
INSERT INTO `wk_admin_user_config` VALUES (114564, 14773, 1, 'ActivityPhrase', 'Customers have no intention', 'Follow-up Recording Phrases');
INSERT INTO `wk_admin_user_config` VALUES (114565, 14773, 1, 'ActivityPhrase', 'The degree of customer intention is moderate, and follow-up will continue', 'Follow-up Recording Phrases');
INSERT INTO `wk_admin_user_config` VALUES (114566, 14773, 1, 'ActivityPhrase', 'Strong customer intention, high probability of transaction', 'Follow-up Recording Phrases');
INSERT INTO `wk_admin_user_config` VALUES (114567, 14773, 1, 'readNotice', '', 'Upgrade log reading status');
INSERT INTO `wk_admin_user_config` VALUES (114568, 14773, 1, 'readNotice', '', 'Upgrade log reading status');
INSERT INTO `wk_admin_user_config` VALUES (114569, 14774, 1, 'ActivityPhrase', 'No one answered the phone', 'Follow-up Recording Phrases');
INSERT INTO `wk_admin_user_config` VALUES (114570, 14774, 1, 'ActivityPhrase', 'Customers have no intention', 'Follow-up Recording Phrases');
INSERT INTO `wk_admin_user_config` VALUES (114571, 14774, 1, 'ActivityPhrase', 'The degree of customer intention is moderate, and follow-up will continue', 'Follow-up Recording Phrases');
INSERT INTO `wk_admin_user_config` VALUES (114572, 14774, 1, 'ActivityPhrase', 'Strong customer intention, high probability of transaction', 'Follow-up Recording Phrases');
INSERT INTO `wk_admin_user_config` VALUES (114573, 14774, 1, 'readNotice', '', 'Upgrade log reading status');

-- ----------------------------
-- Table structure for wk_admin_user_his_table
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_user_his_table`;
CREATE TABLE `wk_admin_user_his_table`  (
  `his_table_id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NULL DEFAULT NULL,
  `his_table` int(0) NULL DEFAULT NULL COMMENT '0 no 1 yes',
  `type` int(0) NULL DEFAULT 1 COMMENT '1. Agent authorization 2. Set default business card 3. Associate employees',
  PRIMARY KEY (`his_table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Authorized agent' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_user_his_table
-- ----------------------------

-- ----------------------------
-- Table structure for wk_admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `wk_admin_user_role`;
CREATE TABLE `wk_admin_user_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT 'User ID',
  `role_id` int(0) NOT NULL COMMENT 'Role id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19221 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'User role correspondence table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_admin_user_role
-- ----------------------------
INSERT INTO `wk_admin_user_role` VALUES (19219, 14773, 180162);
INSERT INTO `wk_admin_user_role` VALUES (19220, 14774, 180162);

-- ----------------------------
-- Table structure for wk_call_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_call_record`;
CREATE TABLE `wk_call_record`  (
  `call_record_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key record id',
  `number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Phone number',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Start ringing time',
  `answer_time` datetime(0) NULL DEFAULT NULL COMMENT 'On time',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT 'End Time',
  `talk_time` int(0) NULL DEFAULT 0 COMMENT 'Call duration (seconds)',
  `dial_time` int(0) NULL DEFAULT 0 COMMENT 'Off-hook time',
  `state` int(0) NULL DEFAULT NULL COMMENT 'Call status (0 not ringing, 1 not connected, 2 connected, 3 incoming call not connected)',
  `type` int(0) NULL DEFAULT NULL COMMENT 'Call type (0 outgoing, 1 incoming)',
  `model` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Associated modules leads, customers, contacts',
  `model_id` int(0) NULL DEFAULT NULL COMMENT 'Associated Module ID',
  `file_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Recording file path',
  `size` int(0) NULL DEFAULT 0 COMMENT 'Recording file size',
  `file_name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'File name',
  `call_upload` tinyint(1) NULL DEFAULT 0 COMMENT '0: CRM server; 1: Upload to Spring Cloud',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Change the time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Batch',
  PRIMARY KEY (`call_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Call records' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_call_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_card_weixin_browse
-- ----------------------------
DROP TABLE IF EXISTS `wk_card_weixin_browse`;
CREATE TABLE `wk_card_weixin_browse`  (
  `browse_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Business card table',
  `weixin_leads_id` bigint(0) NULL DEFAULT NULL COMMENT 'Lead Form',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `num` int(0) NULL DEFAULT 0 COMMENT 'Views',
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`browse_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'browsing business card table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_card_weixin_browse
-- ----------------------------

-- ----------------------------
-- Table structure for wk_card_weixin_leads
-- ----------------------------
DROP TABLE IF EXISTS `wk_card_weixin_leads`;
CREATE TABLE `wk_card_weixin_leads`  (
  `weixin_leads_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `is_transform` int(0) NULL DEFAULT 0 COMMENT '1 Converted 0 Not Converted',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `owner_user_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `weixin_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'avatar',
  `weixin_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'name',
  `weixin_number` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'number',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Phone number',
  `openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Unique ID',
  `sex` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`weixin_leads_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'leads' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_card_weixin_leads
-- ----------------------------

-- ----------------------------
-- Table structure for wk_card_weixin_leads_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_card_weixin_leads_user`;
CREATE TABLE `wk_card_weixin_leads_user`  (
  `weixin_user_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Followed employee id',
  `weixin_leads_id` bigint(0) NULL DEFAULT NULL COMMENT 'lead id',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `relevance_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Employee id',
  PRIMARY KEY (`weixin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Business card holder' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_card_weixin_leads_user
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_achievement
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_achievement`;
CREATE TABLE `wk_crm_achievement`  (
  `achievement_id` int(0) NOT NULL AUTO_INCREMENT,
  `obj_id` int(0) NULL DEFAULT NULL COMMENT 'Object ID',
  `type` int(0) NULL DEFAULT 0 COMMENT '1 company 2 departments 3 employees',
  `year` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Year',
  `january` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'January',
  `february` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'February',
  `march` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'March',
  `april` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'April',
  `may` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'May',
  `june` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'June',
  `july` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'July',
  `august` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'August',
  `september` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'September',
  `october` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'October',
  `november` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'November',
  `december` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'December',
  `status` int(0) NULL DEFAULT NULL COMMENT '1 Sales (Target) 2 Payments (Target)',
  `yeartarget` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'Year target',
  PRIMARY KEY (`achievement_id`) USING BTREE,
  INDEX `obj_id`(`obj_id`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Performance targets' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_achievement
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_action_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_action_record`;
CREATE TABLE `wk_crm_action_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `create_user_id` bigint(0) NOT NULL COMMENT 'Operator ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Ip address',
  `types` int(0) NOT NULL COMMENT 'Module type',
  `action_id` int(0) NULL DEFAULT NULL COMMENT 'The ID of the operated object',
  `object` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Object',
  `behavior` int(0) NULL DEFAULT NULL COMMENT 'Behavior',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Content',
  `detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Details',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `types`(`types`, `action_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Field operation record table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_action_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_activity
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_activity`;
CREATE TABLE `wk_crm_activity`  (
  `activity_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Activity id',
  `type` int(0) NULL DEFAULT NULL COMMENT 'Activity Type 1 Follow Up Record 2 Create Record 3 Opportunity Stage Change 4 Field Check-In',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Follow up type',
  `activity_type` int(0) NOT NULL COMMENT 'Activity Type 1 Lead 2 Customer 3 Contact 4 Product 5 Opportunity 6 Contract 7 Payment 8 Journal 9 Approval 10 Schedule 11 Task 12 Email',
  `activity_type_id` int(0) NOT NULL COMMENT 'Activity TypeId',
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Activities',
  `business_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Related business opportunities',
  `contacts_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Associate a contact',
  `next_time` datetime(0) NULL DEFAULT NULL COMMENT 'Next contact time',
  `status` int(0) NULL DEFAULT 1 COMMENT '0 deleted 1 not deleted',
  `lng` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Longitude',
  `lat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Latitude',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Sign-in address',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Batch id',
  PRIMARY KEY (`activity_id`) USING BTREE,
  INDEX `wk_crm_activity_type_activity_type_index`(`type`, `activity_type`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Crm activity sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_activity
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_activity_relation
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_activity_relation`;
CREATE TABLE `wk_crm_activity_relation`  (
  `r_id` int(0) NOT NULL AUTO_INCREMENT,
  `activity_id` int(0) NOT NULL,
  `type` int(0) NOT NULL COMMENT '3 Contacts 5 Opportunities',
  `type_id` int(0) NOT NULL,
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Event Associated Opportunity Contact Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_activity_relation
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_area
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_area`;
CREATE TABLE `wk_crm_area`  (
  `code_id` int(0) NULL DEFAULT NULL,
  `parent_id` int(0) NULL DEFAULT NULL,
  `city_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Place name list' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_area
-- ----------------------------
INSERT INTO `wk_crm_area` VALUES (000001, 100000, '강원도');
INSERT INTO `wk_crm_area` VALUES (000002, 100000, '경기도');
INSERT INTO `wk_crm_area` VALUES (000003, 100000, '경상남도');
INSERT INTO `wk_crm_area` VALUES (000004, 100000, '경상북도');
INSERT INTO `wk_crm_area` VALUES (000005, 100000, '광주광역시');
INSERT INTO `wk_crm_area` VALUES (000006, 100000, '대구광역시');
INSERT INTO `wk_crm_area` VALUES (000007, 100000, '대전광역시');
INSERT INTO `wk_crm_area` VALUES (000008, 100000, '부산광역시');
INSERT INTO `wk_crm_area` VALUES (000009, 100000, '서울특별시');
INSERT INTO `wk_crm_area` VALUES (000010, 100000, '울산광역시');
INSERT INTO `wk_crm_area` VALUES (000011, 100000, '인천광역시');
INSERT INTO `wk_crm_area` VALUES (000012, 100000, '전라남도');
INSERT INTO `wk_crm_area` VALUES (000013, 100000, '전라북도');
INSERT INTO `wk_crm_area` VALUES (000014, 100000, '제주특별자치도');
INSERT INTO `wk_crm_area` VALUES (000015, 100000, '충청남도');
INSERT INTO `wk_crm_area` VALUES (000016, 100000, '충청북도');

-- ----------------------------
-- Table structure for wk_crm_back_log_deal
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_back_log_deal`;
CREATE TABLE `wk_crm_back_log_deal`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `model` int(0) NOT NULL COMMENT 'To-do module 1 Contact customers today 2 Clues assigned to me 3 Assigned to my clients 4 Clients to enter the high seas 5 Contracts to be reviewed 6 Payment to be reviewed 7 Payment reminders to be received 8 Contracts that are about to expire 9 To be returned Contract 10 Invoices to be reviewed',
  `crm_type` int(0) NOT NULL COMMENT 'Data module',
  `type_id` int(0) NOT NULL COMMENT 'Data id',
  `pool_id` int(0) NULL DEFAULT NULL COMMENT 'High seas id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Founder',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'To-Do Markup Worksheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_back_log_deal
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_business
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_business`;
CREATE TABLE `wk_crm_business`  (
  `business_id` int(0) NOT NULL AUTO_INCREMENT,
  `type_id` int(0) NULL DEFAULT NULL COMMENT 'Opportunity Status Group',
  `status_id` int(0) NULL DEFAULT NULL COMMENT 'Sales stage',
  `next_time` datetime(0) NULL DEFAULT NULL COMMENT 'Next contact time',
  `customer_id` int(0) NULL DEFAULT NULL COMMENT 'Customer ID',
  `contacts_id` int(0) NULL DEFAULT NULL COMMENT 'Primary Contact ID',
  `deal_date` datetime(0) NULL DEFAULT NULL COMMENT 'Estimated closing date',
  `business_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Opportunity name',
  `money` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Amount of business opportunity',
  `discount_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Whole order discount',
  `total_price` decimal(17, 2) NULL DEFAULT NULL COMMENT 'Total product amount',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT 'Remark',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Batches such as attachment batches',
  `ro_user_id` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Read only permission',
  `rw_user_id` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Read and write permissions',
  `is_end` int(0) NOT NULL DEFAULT 0 COMMENT '1 win order 2 lose order 3 invalid',
  `status_remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `status` int(0) NULL DEFAULT 1 COMMENT '1 normal 3 delete',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT 'Last follow-up time',
  `followup` int(0) NULL DEFAULT NULL COMMENT '0 not followed up 1 followed up',
  PRIMARY KEY (`business_id`) USING BTREE,
  INDEX `owner_user_id`(`owner_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Business Opportunity Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_business
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_business_change
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_business_change`;
CREATE TABLE `wk_crm_business_change`  (
  `change_id` int(0) NOT NULL AUTO_INCREMENT,
  `business_id` int(0) NOT NULL COMMENT 'Opportunity ID',
  `status_id` int(0) NOT NULL COMMENT 'Stage ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Founder',
  PRIMARY KEY (`change_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Opportunity Stage Change Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_business_change
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_business_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_business_data`;
CREATE TABLE `wk_crm_business_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Opportunity Extended Fields Data Sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_business_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_business_product
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_business_product`;
CREATE TABLE `wk_crm_business_product`  (
  `r_id` int(0) NOT NULL AUTO_INCREMENT,
  `business_id` int(0) NOT NULL COMMENT 'Opportunity ID',
  `product_id` int(0) NOT NULL COMMENT 'Product ID',
  `price` decimal(18, 2) NOT NULL COMMENT 'Product unit price',
  `sales_price` decimal(18, 2) NOT NULL COMMENT 'Selling price',
  `num` decimal(10, 2) NOT NULL COMMENT 'Quantity',
  `discount` int(0) NOT NULL COMMENT 'Discount',
  `subtotal` decimal(18, 2) NOT NULL COMMENT 'Subtotal (price after discount)',
  `unit` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Unit',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Opportunity Product Relationship Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_business_product
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_business_status
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_business_status`;
CREATE TABLE `wk_crm_business_status`  (
  `status_id` int(0) NOT NULL AUTO_INCREMENT,
  `type_id` int(0) NOT NULL COMMENT 'Opportunity Status Category ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Logo',
  `rate` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Win rate',
  `order_num` int(0) NULL DEFAULT NULL COMMENT 'Sort',
  PRIMARY KEY (`status_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47646 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Opportunity Status' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_business_status
-- ----------------------------
INSERT INTO `wk_crm_business_status` VALUES (47643, 12366, 'Authenticate customers', '20', 1);
INSERT INTO `wk_crm_business_status` VALUES (47644, 12366, 'Demand analysis', '30', 2);
INSERT INTO `wk_crm_business_status` VALUES (47645, 12366, 'Scheme/Quotation', '80', 3);

-- ----------------------------
-- Table structure for wk_crm_business_type
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_business_type`;
CREATE TABLE `wk_crm_business_type`  (
  `type_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Logo',
  `dept_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Department ID',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '0 disable 1 enable 2 delete',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12367 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Opportunity Status Group Category' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_business_type
-- ----------------------------
INSERT INTO `wk_crm_business_type` VALUES (12366, 'Sales Process Business Group', '', 3, '2019-05-11 16:25:09', NULL, 1);

-- ----------------------------
-- Table structure for wk_crm_business_user_star
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_business_user_star`;
CREATE TABLE `wk_crm_business_user_star`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  `business_id` int(0) NOT NULL COMMENT 'Customer id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `business_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'User Opportunity Mark-Star Relationship Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_business_user_star
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_contacts
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_contacts`;
CREATE TABLE `wk_crm_contacts`  (
  `contacts_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contact name',
  `next_time` datetime(0) NULL DEFAULT NULL COMMENT 'Next contact time',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Cell phone',
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Telephone',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'E-mail',
  `post` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Job title',
  `customer_id` int(0) NOT NULL COMMENT 'Customer ID',
  `address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Address',
  `remark` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT 'Remark',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Batch',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT 'Last follow-up time',
  PRIMARY KEY (`contacts_id`) USING BTREE,
  INDEX `owner_user_id`(`owner_user_id`) USING BTREE,
  INDEX `customer_id`(`customer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Contact form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_contacts
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_contacts_business
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_contacts_business`;
CREATE TABLE `wk_crm_contacts_business`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `business_id` int(0) NOT NULL,
  `contacts_id` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Opportunity Contact Association Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_contacts_business
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_contacts_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_contacts_data`;
CREATE TABLE `wk_crm_contacts_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Contact extension field data table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_contacts_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_contacts_user_star
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_contacts_user_star`;
CREATE TABLE `wk_crm_contacts_user_star`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  `contacts_id` int(0) NOT NULL COMMENT 'Customer id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `contacts_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'User contact star relationship table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_contacts_user_star
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_contract
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_contract`;
CREATE TABLE `wk_crm_contract`  (
  `contract_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contract title',
  `customer_id` int(0) NULL DEFAULT NULL COMMENT 'Customer ID',
  `business_id` int(0) NULL DEFAULT NULL COMMENT 'Opportunity ID',
  `check_status` int(0) NOT NULL DEFAULT 0 COMMENT '0 pending review, 1 passed, 2 rejected, 3 under review 4: withdrawn 5 not submitted 6 created 7 deleted 8 void',
  `examine_record_id` int(0) NULL DEFAULT NULL COMMENT 'Audit record ID',
  `order_date` datetime(0) NULL DEFAULT NULL COMMENT 'Order date',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contract Number',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Starting time',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT 'End Time',
  `money` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Contract amount',
  `discount_rate` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Whole order discount',
  `total_price` decimal(17, 2) NULL DEFAULT NULL COMMENT 'Total product amount',
  `types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Type of contract',
  `payment_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Payment method',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Batches such as attachment batches',
  `ro_user_id` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Read only permission',
  `rw_user_id` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Read and write permissions',
  `contacts_id` int(0) NULL DEFAULT NULL COMMENT 'Customer signatory (contact id)',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Remark',
  `company_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Company signatory',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT 'Last follow-up time',
  `received_money` decimal(17, 2) NULL DEFAULT 0.00,
  `unreceived_money` decimal(17, 2) NULL DEFAULT NULL,
  `old_contract_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`contract_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Contract form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_contract
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_contract_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_contract_data`;
CREATE TABLE `wk_crm_contract_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Contract Extended Field Data Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_contract_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_contract_product
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_contract_product`;
CREATE TABLE `wk_crm_contract_product`  (
  `r_id` int(0) NOT NULL AUTO_INCREMENT,
  `contract_id` int(0) NOT NULL COMMENT 'Contract ID',
  `product_id` int(0) NOT NULL COMMENT 'Product ID',
  `price` decimal(18, 2) NOT NULL COMMENT 'Product unit price',
  `sales_price` decimal(18, 2) NOT NULL COMMENT 'Selling price',
  `num` decimal(10, 2) NOT NULL COMMENT 'Quantity',
  `discount` decimal(18, 4) NOT NULL COMMENT 'Discount',
  `subtotal` decimal(18, 2) NOT NULL COMMENT 'Subtotal (price after discount)',
  `unit` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Unit',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Contract product relationship table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_contract_product
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer`;
CREATE TABLE `wk_crm_customer`  (
  `customer_id` int(0) NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Client\'s name',
  `followup` int(0) NULL DEFAULT NULL COMMENT 'Follow up status 0 not followed up 1 followed up',
  `is_lock` int(0) NOT NULL DEFAULT 0 COMMENT '1 lock',
  `next_time` datetime(0) NULL DEFAULT NULL COMMENT 'Next contact time',
  `deal_status` int(0) NULL DEFAULT 0 COMMENT 'Transaction Status 0 Not Transaction 1 Transaction',
  `deal_time` datetime(0) NULL DEFAULT NULL COMMENT 'Transaction time',
  `contacts_id` int(0) NULL DEFAULT NULL COMMENT 'Primary Contact ID',
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Cell phone',
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Telephone',
  `website` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'URL',
  `email` varchar(225) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Mail',
  `remark` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Remark',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `ro_user_id` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Read only permission',
  `rw_user_id` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Read and write permissions',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Province City District',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Positioning information',
  `detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Address',
  `lng` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Geographical Longitude',
  `lat` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Geographic dimension',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Batches such as attachment batches',
  `status` int(0) NULL DEFAULT 1 COMMENT 'Customer Status 1 Normal 2 Locked 3 Deleted',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT 'Last follow-up time',
  `pool_time` datetime(0) NULL DEFAULT NULL COMMENT 'Put in high sea time',
  `is_receive` int(0) NULL DEFAULT NULL COMMENT '1 assign 2 receive',
  `last_content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Last follow up record',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT 'Received customer time',
  `pre_owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'ID of the person in charge before entering the high seas',
  PRIMARY KEY (`customer_id`) USING BTREE,
  INDEX `update_time`(`update_time`) USING BTREE,
  INDEX `owner_user_id`(`owner_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Customer table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_data`;
CREATE TABLE `wk_crm_customer_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Customer Extended Field Data Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer_pool
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_pool`;
CREATE TABLE `wk_crm_customer_pool`  (
  `pool_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'High seas id',
  `pool_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'High seas name',
  `admin_user_id` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Admin "," split',
  `member_user_id` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'High Seas Rules Staff Member "," Split',
  `member_dept_id` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Members of the High Seas Rules Division "," split',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT 'Status 0 Disable 1 Enable',
  `pre_owner_setting` int(0) NOT NULL COMMENT 'The former person in charge of the collection rules 0 unlimited 1 limited',
  `pre_owner_setting_day` int(0) NULL DEFAULT NULL COMMENT 'The number of days limited by the former person in charge of the collection rules',
  `receive_setting` int(0) NOT NULL COMMENT 'Whether to limit the frequency of receiving 0 no limit 1 limit',
  `receive_num` int(0) NULL DEFAULT NULL COMMENT 'Collection frequency rules',
  `remind_setting` int(0) NOT NULL COMMENT 'Whether to set advance reminder 0 not open 1 open',
  `remind_day` int(0) NULL DEFAULT NULL COMMENT 'Reminder rule days',
  `put_in_rule` int(0) NOT NULL COMMENT 'Recall rule 0 not automatically reclaimed 1 automatic reclaim',
  `create_user_id` bigint(0) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`pool_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34553 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'High seas watch' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_pool
-- ----------------------------
INSERT INTO `wk_crm_customer_pool` VALUES (34552, 'System default high seas', '14773', '14773', '', 1, 0, NULL, 0, NULL, 0, NULL, 0, 0, '2019-06-30 18:13:08');

-- ----------------------------
-- Table structure for wk_crm_customer_pool_field_setting
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_pool_field_setting`;
CREATE TABLE `wk_crm_customer_pool_field_setting`  (
  `setting_id` int(0) NOT NULL AUTO_INCREMENT,
  `pool_id` int(0) NOT NULL COMMENT 'High seas id',
  `field_id` int(0) NULL DEFAULT NULL COMMENT 'Field id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Korean name',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `type` int(0) NOT NULL COMMENT 'Field Type 1 Single Line Text 2 Multi Line Text 3 Single Choice 4 Date 5 Number 6 Decimal 7 Mobile Phone 8 File 9 Multiple Choice 10 Person 11 Attachment 12 Department 13 Date Time 14 Email 15 Customer 16 Business Opportunity 17 Contact 18 Map 19 Product Type 20 Contract 21 Repayment plan',
  `is_hidden` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to hide 0 not to hide 1 to hide',
  PRIMARY KEY (`setting_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 439856 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'High seas list page field setting table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_pool_field_setting
-- ----------------------------
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439842, 34552, 1101827, 'Client\'s name', 'customerName', 1, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439843, 34552, 1101829, 'Cell phone', 'mobile', 7, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439844, 34552, 1101830, 'Telephone', 'telephone', 1, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439845, 34552, 1101831, 'URL', 'website', 1, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439846, 34552, 1101834, 'Next contact time', 'nextTime', 13, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439847, 34552, 1101835, 'Remark', 'remark', 1, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439848, 34552, 1101833, 'Customer level', 'level', 3, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439849, 34552, 1101828, 'Customer source', 'source', 3, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439850, 34552, 1101832, 'Client industry', 'industry', 3, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439851, 34552, NULL, 'Transaction status', 'dealStatus', 3, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439852, 34552, NULL, 'Last follow-up time', 'lastTime', 4, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439853, 34552, NULL, 'Update time', 'updateTime', 4, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439854, 34552, NULL, 'Creation time', 'createTime', 4, 0);
INSERT INTO `wk_crm_customer_pool_field_setting` VALUES (439855, 34552, NULL, 'Founder', 'createUserName', 1, 0);

-- ----------------------------
-- Table structure for wk_crm_customer_pool_field_sort
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_pool_field_sort`;
CREATE TABLE `wk_crm_customer_pool_field_sort`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `pool_id` int(0) NOT NULL COMMENT 'High seas id',
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  `field_id` int(0) NULL DEFAULT NULL COMMENT 'Field id',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Korean name',
  `type` int(0) NOT NULL COMMENT 'Field Type 1 Single Line Text 2 Multi Line Text 3 Single Choice 4 Date 5 Number 6 Decimal 7 Mobile Phone 8 File 9 Multiple Choice 10 Person 11 Attachment 12 Department 13 Date Time 14 Email 15 Customer 16 Business Opportunity 17 Contact 18 Map 19 Product Type 20 Contract 21 Repayment plan',
  `sort` int(0) NOT NULL COMMENT 'Sort by field',
  `is_hidden` int(0) NOT NULL COMMENT 'Whether to hide 0, not hide 1, hide',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'High seas list page field sorting table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_pool_field_sort
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer_pool_field_style
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_pool_field_style`;
CREATE TABLE `wk_crm_customer_pool_field_style`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `pool_id` int(0) NOT NULL COMMENT 'High seas id',
  `style` int(0) NOT NULL COMMENT 'Field width',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'High seas list page field style sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_pool_field_style
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer_pool_relation
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_pool_relation`;
CREATE TABLE `wk_crm_customer_pool_relation`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `customer_id` int(0) NOT NULL COMMENT 'Customer id',
  `pool_id` int(0) NOT NULL COMMENT 'High seas id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `pool_id`(`pool_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Customer High Seas Relation Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_pool_relation
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer_pool_rule
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_pool_rule`;
CREATE TABLE `wk_crm_customer_pool_rule`  (
  `rule_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Retract rule id',
  `pool_id` int(0) NOT NULL COMMENT 'High seas id',
  `type` int(0) NOT NULL COMMENT 'Judgment type of withdrawal rules 1 Follow-up record 2 Business opportunity 3 Transaction status',
  `deal_handle` int(0) NULL DEFAULT NULL COMMENT 'Whether the customer has entered the high seas 0 do not enter 1 enter',
  `business_handle` int(0) NULL DEFAULT NULL COMMENT 'Whether customers with business opportunities enter the high seas 0 do not enter 1 enter',
  `customer_level_setting` int(0) NOT NULL COMMENT 'Customer level setting 1All 2Set separately according to the level',
  `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Customer Level 1 All',
  `limit_day` int(0) NOT NULL COMMENT 'High Seas Rule Restricted Days',
  PRIMARY KEY (`rule_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'High Seas Recovery Rules Sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_pool_rule
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer_setting
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_setting`;
CREATE TABLE `wk_crm_customer_setting`  (
  `setting_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `setting_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Rule name',
  `customer_num` int(0) NULL DEFAULT NULL COMMENT 'Number of customers you can have',
  `customer_deal` int(0) NULL DEFAULT 0 COMMENT 'Whether the transaction customer occupies the quantity 0 does not occupy 1 occupies',
  `type` int(0) NULL DEFAULT NULL COMMENT 'Type 1 Has Customer Limit 2 Locked Customer Limit',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`setting_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee Owned and Locked Client Limits' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_setting
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer_setting_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_setting_user`;
CREATE TABLE `wk_crm_customer_setting_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `setting_id` int(0) NOT NULL COMMENT 'Customer Rule Restriction ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Userid',
  `dept_id` int(0) NULL DEFAULT NULL COMMENT 'Department ID',
  `type` int(0) NULL DEFAULT NULL COMMENT '1 employee 2 department',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee owned and locked customer employee association table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_setting_user
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer_stats_2021
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_stats_2021`;
CREATE TABLE `wk_crm_customer_stats_2021`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key',
  `customer_num` bigint(0) NULL DEFAULT NULL COMMENT 'Number of clients',
  `deal_status` int(0) NULL DEFAULT NULL COMMENT 'Transaction Status 0 Not Transaction 1 Transaction',
  `owner_user_id` int(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `create_date` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Created Year Month Day',
  `deal_date` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Created Year Month Day',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `id`(`customer_num`, `deal_status`, `owner_user_id`, `create_date`, `deal_date`) USING BTREE,
  INDEX `create_date`(`create_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_stats_2021
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer_stats_info
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_stats_info`;
CREATE TABLE `wk_crm_customer_stats_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `last_customer_id` int(0) NOT NULL COMMENT 'Last sync client id',
  `create_time` datetime(0) NOT NULL COMMENT 'Synchronised time',
  `sync_num` int(0) NULL DEFAULT NULL COMMENT 'Number of syncs',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 130 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Statistics summary table of number of customers' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_stats_info
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_customer_user_star
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_customer_user_star`;
CREATE TABLE `wk_crm_customer_user_star`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  `customer_id` int(0) NOT NULL COMMENT 'Customer id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `customer_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'User customer star relationship table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_customer_user_star
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_examine
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_examine`;
CREATE TABLE `wk_crm_examine`  (
  `examine_id` int(0) NOT NULL AUTO_INCREMENT,
  `category_type` int(0) NOT NULL DEFAULT 1 COMMENT '1 Contract 2 Receipt 3 Invoice 4 Salary 5 Purchase review 6 Purchase return review 7 Sales review 8 Sales return review 9 Payment slip review 10 Payment receipt review 11 Inventory review 12 Transfer review',
  `examine_type` int(0) NULL DEFAULT NULL COMMENT 'Approval Type 1 Fixed Approval 2 Authorized Approval',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Approval flow name',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Icon',
  `dept_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT 'Department ID (0 is all)',
  `user_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT 'Employee ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Founder',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Change the time',
  `update_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Modified by',
  `status` int(0) NULL DEFAULT NULL COMMENT 'Status 1 Enable 0 Disable 2 Delete',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Flow Description',
  PRIMARY KEY (`examine_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25378 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Approval process table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_examine
-- ----------------------------
INSERT INTO `wk_crm_examine` VALUES (25375, 2, 2, 'Payment Approval Process', NULL, NULL, NULL, NULL, 3, NULL, 3, 1, '');
INSERT INTO `wk_crm_examine` VALUES (25376, 1, 2, 'Contract approval process', NULL, NULL, NULL, NULL, 3, NULL, 3, 1, 'Illustrate');
INSERT INTO `wk_crm_examine` VALUES (25377, 3, 2, 'Invoice Approval Process', NULL, NULL, NULL, NULL, 0, NULL, 0, 1, '');

-- ----------------------------
-- Table structure for wk_crm_examine_log
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_examine_log`;
CREATE TABLE `wk_crm_examine_log`  (
  `log_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `record_id` int(0) NULL DEFAULT NULL COMMENT 'Approval record ID',
  `examine_step_id` bigint(0) NULL DEFAULT NULL COMMENT 'Audit Step ID',
  `examine_status` int(0) NULL DEFAULT NULL COMMENT 'Approval Status 0 Unapproved 1 Approved 2 Approved Rejected 3 Withdrawn Approval',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT 'Founder',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `examine_user` bigint(0) NULL DEFAULT NULL COMMENT 'Reviewer',
  `examine_time` datetime(0) NULL DEFAULT NULL COMMENT 'Review time',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Review Notes',
  `is_recheck` int(0) NULL DEFAULT 0 COMMENT 'Whether it is the log before the withdrawal 0 or null for the new data 1: the data before the withdrawal',
  `order_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Audit log table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_examine_log
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_examine_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_examine_record`;
CREATE TABLE `wk_crm_examine_record`  (
  `record_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Audit record ID',
  `examine_id` int(0) NULL DEFAULT NULL COMMENT 'Approval ID',
  `examine_step_id` bigint(0) NULL DEFAULT NULL COMMENT 'Approval step ID currently in progress',
  `examine_status` int(0) NULL DEFAULT NULL COMMENT 'Review status 0 Not reviewed 1 Review passed 2 Review rejected 3 Reviewed 4 Withdrawn',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT 'Founder',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Review Notes',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Audit record sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_examine_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_examine_step
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_examine_step`;
CREATE TABLE `wk_crm_examine_step`  (
  `step_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `step_type` int(0) NULL DEFAULT NULL COMMENT 'Step type 1. Responsible person in charge, 2. Designated user (any one person), 3. Designated user (multi-person countersignment), 4. Supervisor of the upper-level approver',
  `examine_id` int(0) NOT NULL COMMENT 'Approval ID',
  `check_user_id` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Approver IDs (separated by commas) ,1,2,',
  `step_num` int(0) NULL DEFAULT 1 COMMENT 'Sort',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  PRIMARY KEY (`step_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Approval step table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_examine_step
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_field
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_field`;
CREATE TABLE `wk_crm_field`  (
  `field_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `field_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Custom field English identification',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Field Name',
  `type` int(0) NOT NULL DEFAULT 1 COMMENT 'Field Type 1 Single Line Text 2 Multi Line Text 3 Single Choice 4 Date 5 Number 6 Decimal 7 Mobile Phone 8 File 9 Multiple Choice 10 Person 11 Attachment 12 Department 13 Date Time 14 Email 15 Customer 16 Business Opportunity 17 Contact 18 Map 19 Product Type 20 Contract 21 Repayment plan',
  `label` int(0) NOT NULL COMMENT 'Labels 1 Leads 2 Customers 3 Contacts 4 Products 5 Business Opportunities 6 Contracts 7 Repayment 8. Repayment Program',
  `remark` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field Description',
  `input_tips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Input hint',
  `max_length` int(0) NULL DEFAULT NULL COMMENT 'The maximum length',
  `default_value` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Defaults',
  `is_unique` int(0) NULL DEFAULT 0 COMMENT 'Is it unique 1 yes 0 no',
  `is_null` int(0) NULL DEFAULT 0 COMMENT 'Required 1 Yes 0 No',
  `sorting` int(0) NULL DEFAULT 1 COMMENT 'Sort from smallest to largest',
  `options` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'If the type is an option, it cannot be empty here. Multiple options are separated by',
  `operating` int(0) NULL DEFAULT 255 COMMENT 'Is it possible to delete modifications',
  `is_hidden` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to hide 0 not to hide 1 to hide',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Last Modified',
  `field_type` int(0) NOT NULL DEFAULT 0 COMMENT 'Field source 0. Custom 1. Original fixed 2 Original field but the value exists in the extension table',
  `relevant` int(0) NULL DEFAULT NULL COMMENT 'Convert customer\'s custom field ID only if the lead needs it',
  `style_percent` int(0) NULL DEFAULT 50 COMMENT 'Style percentage %',
  `precisions` int(0) NULL DEFAULT NULL COMMENT 'Precision, maximum allowed decimal places',
  `form_position` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Form positioning Coordinate format: 1,1',
  `max_num_restrict` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Maximum value limit',
  `min_num_restrict` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'The minimum value of the limit',
  `form_assist_id` int(0) NULL DEFAULT NULL COMMENT 'Form auxiliary id, front-end generated',
  PRIMARY KEY (`field_id`) USING BTREE,
  INDEX `label`(`label`) USING BTREE,
  INDEX `update_time`(`update_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1101914 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Custom field table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_field
-- ----------------------------
INSERT INTO `wk_crm_field` VALUES (1101827, 'customer_name', 'Client\'s name', 1, 2, NULL, NULL, 255, '', 1, 1, 0, NULL, 189, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101828, 'source', 'Customer source', 3, 2, NULL, NULL, NULL, '', 0, 0, 1, 'Promotion, search engine, advertisement, referral, online registration, online inquiry, door-to-door appointment, Mobile, telephone consultation, email consultation', 191, 0, '2021-03-24 16:55:24', 2, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101829, 'mobile', 'Cell phone', 7, 2, NULL, NULL, 255, '', 0, 0, 2, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101830, 'telephone', 'Telephone', 1, 2, NULL, NULL, 255, '', 0, 0, 3, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101831, 'website', 'URL', 1, 2, NULL, NULL, 255, '', 0, 0, 4, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101832, 'industry', 'Client industry', 3, 2, NULL, NULL, NULL, '', 0, 0, 5, 'IT, Finance, Real Estate, Business Services, Transportation/Logistics, Production, Government, Cultural Media', 191, 0, '2021-03-24 16:55:24', 2, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101833, 'level', 'Customer level', 3, 2, NULL, NULL, NULL, '', 0, 0, 6, 'A (key customers), B (ordinary customers), C (non-priority customers)', 63, 0, '2021-03-24 16:55:24', 2, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101834, 'next_time', 'Next contact time', 13, 2, NULL, NULL, NULL, '', 0, 0, 7, NULL, 63, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101835, 'remark', 'Remark', 2, 2, NULL, NULL, 255, '', 0, 0, 8, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101836, 'email', 'Mail', 14, 2, NULL, NULL, 255, '', 0, 0, 4, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101837, 'leads_name', 'Lead name', 1, 1, NULL, NULL, 255, '', 0, 1, 0, NULL, 189, 0, '2021-03-24 16:55:24', 1, 1101827, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101838, 'email', 'Mail', 14, 1, NULL, NULL, 255, '', 0, 0, 1, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101839, 'source', 'Lead source', 3, 1, NULL, NULL, NULL, '', 0, 0, 2, 'Promotion, search engine, advertisement, referral, online registration, online inquiry, door-to-door appointment, Mobile, telephone consultation, email consultation', 191, 0, '2021-03-24 16:55:24', 2, 1101828, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101840, 'mobile', 'Cell phone', 7, 1, NULL, NULL, 255, '', 0, 0, 3, NULL, 191, 0, '2021-03-24 16:55:24', 1, 1101829, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101841, 'telephone', 'Telephone', 1, 1, NULL, NULL, 255, '', 0, 0, 4, NULL, 191, 0, '2021-03-24 16:55:24', 1, 1101830, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101842, 'address', 'Address', 1, 1, NULL, NULL, 255, '', 0, 0, 5, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101843, 'industry', 'Client industry', 3, 1, NULL, NULL, NULL, '', 0, 0, 6, 'IT, Finance, Real Estate, Business Services, Transportation/Logistics, Production, Government, Cultural Media', 191, 0, '2021-03-24 16:55:24', 2, 1101832, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101844, 'level', 'Customer level', 3, 1, NULL, NULL, NULL, '', 0, 0, 7, 'A (key customers), B (ordinary customers), C (non-priority customers)', 191, 0, '2021-03-24 16:55:24', 2, 1101833, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101845, 'next_time', 'Next contact time', 13, 1, NULL, NULL, NULL, '', 0, 0, 8, NULL, 63, 0, '2021-03-24 16:55:24', 1, 1101834, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101846, 'remark', 'Remark', 2, 1, NULL, NULL, 255, '', 0, 0, 9, NULL, 191, 0, '2021-03-24 16:55:24', 1, 1101835, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101847, 'name', 'Name', 1, 3, NULL, NULL, 255, '', 0, 1, 0, NULL, 181, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101848, 'customer_id', 'Client\'s name', 15, 3, NULL, NULL, NULL, '', 0, 1, 1, NULL, 159, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101849, 'mobile', 'Cell phone', 7, 3, NULL, NULL, 255, '', 0, 0, 2, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101850, 'telephone', 'Telephone', 1, 3, NULL, NULL, 255, '', 0, 0, 3, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101851, 'email', 'Mail', 14, 3, NULL, NULL, 255, '', 0, 0, 4, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101852, 'post', 'Job title', 1, 3, NULL, NULL, 255, '', 0, 0, 5, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101853, 'policymakers', 'Key decision maker', 3, 3, NULL, NULL, NULL, '', 0, 0, 6, 'Whether', 190, 0, '2021-03-24 16:55:24', 2, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101854, 'address', 'Address', 1, 3, NULL, NULL, 255, '', 0, 0, 7, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101855, 'next_time', 'Next contact time', 13, 3, NULL, NULL, NULL, '', 0, 0, 8, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101856, 'remark', 'Remark', 2, 3, NULL, NULL, 255, '', 0, 0, 9, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101857, 'sex', 'Gender', 3, 3, NULL, NULL, NULL, '', 0, 0, 10, 'Men and women', 191, 0, '2021-03-24 16:55:24', 2, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101858, 'name', 'Product name', 1, 4, NULL, NULL, 255, '', 0, 1, 0, NULL, 177, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101859, 'category_id', 'Product type', 19, 4, NULL, NULL, NULL, '', 0, 1, 1, NULL, 1, 0, NULL, 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101860, 'unit', 'Product unit', 3, 4, NULL, NULL, NULL, '', 0, 0, 2, 'Piece, block, only, put, piece, bottle, box, table, ton, kilogram, meter, box, set', 191, 0, '2021-03-24 16:55:24', 2, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101861, 'num', 'Product code', 1, 4, NULL, NULL, 255, '', 1, 1, 3, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101862, 'price', 'Price', 6, 4, NULL, NULL, 255, '', 0, 1, 4, NULL, 181, 0, '2021-05-07 13:58:21', 1, NULL, 50, 2, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101863, 'description', 'Product Description', 1, 4, NULL, NULL, 255, '', 0, 0, 6, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101864, 'business_name', 'Opportunity name', 1, 5, NULL, NULL, 255, '', 0, 1, 0, NULL, 181, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101865, 'customer_id', 'Client\'s name', 15, 5, NULL, NULL, NULL, '', 0, 1, 1, NULL, 149, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101866, 'money', 'Amount of business opportunity', 6, 5, NULL, NULL, 255, '', 0, 0, 2, NULL, 189, 0, '2021-05-07 13:58:21', 1, NULL, 50, 2, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101867, 'deal_date', 'Estimated closing date', 13, 5, NULL, NULL, NULL, '', 0, 0, 3, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101868, 'remark', 'Remark', 2, 5, NULL, NULL, 255, '', 0, 0, 4, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101869, 'name', 'Contract title', 1, 6, NULL, NULL, 255, '', 0, 1, 1, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101870, 'num', 'Contract Number', 1, 6, NULL, NULL, 255, '', 1, 1, 0, NULL, 177, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101871, 'customer_id', 'Client\'s name', 15, 6, NULL, NULL, NULL, '', 0, 1, 2, NULL, 149, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101872, 'business_id', 'Opportunity name', 16, 6, NULL, NULL, NULL, '', 0, 0, 3, NULL, 159, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101873, 'money', 'Contract amount', 6, 6, NULL, NULL, 255, '', 0, 1, 4, NULL, 189, 0, '2021-05-07 13:58:21', 1, NULL, 50, 2, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101874, 'order_date', 'Order time', 4, 6, NULL, NULL, NULL, '', 0, 1, 5, NULL, 181, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101875, 'start_time', 'Contract start time', 4, 6, NULL, NULL, NULL, '', 0, 0, 6, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101876, 'end_time', 'Contract end time', 4, 6, NULL, NULL, NULL, '', 0, 0, 7, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101877, 'contacts_id', 'Customer signatory', 17, 6, NULL, NULL, NULL, '', 0, 0, 8, NULL, 159, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101878, 'company_user_id', 'Company signatory', 10, 6, NULL, NULL, NULL, '', 0, 0, 9, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101879, 'remark', 'Remark', 2, 6, NULL, NULL, 255, '', 0, 0, 10, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101880, 'flied_xucqai', 'Type of contract', 3, 6, NULL, NULL, 255, '', 0, 0, 11, 'Direct sales contract, agency contract, service contract, express sales contract', 255, 0, '2021-03-24 16:55:24', 0, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101881, 'number', 'Payment number', 1, 7, NULL, NULL, 255, '', 1, 1, 0, NULL, 177, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101882, 'customer_id', 'Client\'s name', 15, 7, NULL, NULL, NULL, '', 0, 1, 1, NULL, 149, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101883, 'contract_id', 'Contract Number', 20, 7, NULL, NULL, NULL, '', 0, 1, 2, NULL, 159, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101884, 'receivables_plan_id', 'Period', 21, 7, NULL, NULL, NULL, '', 0, 0, 3, NULL, 1, 0, '2021-08-02 11:10:31', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101885, 'return_time', 'Payment date', 4, 7, NULL, NULL, NULL, '', 0, 1, 4, NULL, 181, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101886, 'money', 'Repayment amount', 6, 7, NULL, NULL, 255, '', 0, 1, 5, NULL, 181, 0, '2021-05-07 13:58:21', 1, NULL, 50, 2, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101887, 'return_type', 'Payment method', 3, 7, NULL, NULL, NULL, '', 0, 0, 6, 'Check, Cash, Postal Remittance, Wire Transfer, Online Transfer, Others', 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101888, 'remark', 'Remark', 2, 7, NULL, NULL, 255, '', 0, 0, 7, NULL, 191, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101889, 'customer_id', 'Client\'s name', 15, 8, NULL, NULL, NULL, '', 0, 1, 1, NULL, 181, 0, '2021-08-02 11:01:53', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101890, 'contract_id', 'Contract Number', 20, 8, NULL, NULL, 11, '', 0, 1, 2, NULL, 181, 0, '2021-08-02 11:01:53', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101891, 'money', 'Planned repayment amount', 6, 8, NULL, NULL, NULL, '', 0, 1, 3, NULL, 181, 0, '2021-08-02 11:01:53', 1, NULL, 50, 2, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101892, 'return_date', 'Scheduled payment date', 4, 8, NULL, NULL, NULL, '', 0, 1, 4, NULL, 183, 0, '2021-08-02 11:01:53', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101893, 'remind', 'Reminder a few days in advance', 5, 8, NULL, NULL, 11, '', 0, 0, 5, NULL, 1, 0, NULL, 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101894, 'remark', 'Remark', 2, 8, NULL, NULL, 1000, '', 0, 0, 6, NULL, 1, 0, NULL, 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101895, 'visit_number', 'Return visit number', 1, 17, NULL, NULL, NULL, '', 1, 1, 0, NULL, 177, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101896, 'visit_time', 'Return visit time', 13, 17, NULL, NULL, NULL, '', 0, 1, 1, NULL, 181, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101897, 'owner_user_id', 'Return visitor', 28, 17, NULL, NULL, NULL, '', 0, 1, 2, NULL, 149, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101898, 'return_visit_type', 'Return visit form', 3, 17, NULL, NULL, NULL, '', 0, 0, 3, 'Meet and Greet, Phone, SMS, Email', 191, 0, '2021-03-24 16:55:24', 2, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101899, 'customer_id', 'Client\'s name', 15, 17, NULL, NULL, NULL, '', 0, 1, 4, NULL, 149, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101900, 'contacts_id', 'Contact', 17, 17, NULL, NULL, NULL, '', 0, 0, 5, NULL, 159, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101901, 'contract_id', 'Contract Number', 20, 17, NULL, NULL, NULL, '', 0, 1, 6, NULL, 159, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101902, 'satisficing', 'Customer satisfaction', 3, 17, NULL, NULL, NULL, '', 0, 0, 7, 'Very satisfied, satisfied, average, dissatisfied, very dissatisfied', 191, 0, '2021-03-24 16:55:24', 2, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101903, 'flied_itvzix', 'Client feedback', 2, 17, NULL, NULL, 1000, '', 0, 0, 8, NULL, 191, 0, '2021-03-24 16:55:24', 0, NULL, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101905, 'invoice_apply_number', 'Invoice application number', 1, 18, NULL, NULL, NULL, '', 1, 1, 0, NULL, 176, 0, '2021-05-07 13:58:07', 1, NULL, 50, NULL, '0,0', NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101906, 'customer_id', 'Client\'s name', 15, 18, NULL, NULL, NULL, '', 0, 1, 1, NULL, 148, 0, '2021-05-07 13:58:07', 1, NULL, 50, NULL, '0,1', NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101907, 'contract_id', 'Contract Number', 20, 18, NULL, NULL, NULL, '', 0, 1, 2, NULL, 148, 0, '2021-05-07 13:58:07', 1, NULL, 50, NULL, '1,0', NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101908, 'contract_money', 'Contract amount', 6, 18, NULL, NULL, NULL, '', 0, 0, 3, NULL, 144, 0, '2021-05-07 13:58:07', 1, NULL, 50, 2, '1,1', NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101909, 'invoice_money', 'Invoice amount', 6, 18, NULL, NULL, NULL, '', 0, 1, 4, NULL, 148, 0, '2021-05-07 13:58:07', 1, NULL, 50, 2, '2,0', NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101910, 'invoice_date', 'Billing date', 13, 18, NULL, NULL, NULL, '', 0, 0, 5, NULL, 190, 0, '2021-05-07 13:58:07', 1, NULL, 50, NULL, '2,1', NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101911, 'invoice_type', 'Billing type', 3, 18, NULL, NULL, NULL, '', 0, 1, 6, 'VAT special invoices, VAT ordinary invoices, national tax general machine-printed invoices, local tax general machine-printed invoices, receipts', 158, 0, '2021-05-07 13:58:07', 1, NULL, 50, NULL, '3,0', NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101912, 'remark', 'Remark', 2, 18, NULL, NULL, 255, '', 0, 0, 7, NULL, 190, 0, '2021-05-07 13:58:07', 1, NULL, 50, NULL, '3,1', NULL, NULL, NULL);
INSERT INTO `wk_crm_field` VALUES (1101913, 'return_type', 'Payment method', 3, 8, NULL, NULL, NULL, '', 0, 0, 6, 'Check, Cash, Postal Remittance, Wire Transfer, Online Transfer, Others', 0, 0, '2021-03-24 16:55:24', 1, NULL, 50, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wk_crm_field_config
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_field_config`;
CREATE TABLE `wk_crm_field_config`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `field_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Field Name',
  `field_type` int(0) NOT NULL DEFAULT 1 COMMENT 'Field Type 1 keyword 2 date 3 number 4 nested 5 datetime',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `label` int(0) NOT NULL COMMENT 'label',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `field_name`(`field_name`, `label`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100294 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Field configuration table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_field_config
-- ----------------------------
INSERT INTO `wk_crm_field_config` VALUES (100257, 'flied_dzmbcn', 4, '2020-08-03 18:46:43', 6);
INSERT INTO `wk_crm_field_config` VALUES (100258, 'flied_ivcdhc', 4, '2020-08-03 18:47:30', 3);
INSERT INTO `wk_crm_field_config` VALUES (100259, 'flied_pyrnyn', 1, '2020-08-03 19:12:42', 1);
INSERT INTO `wk_crm_field_config` VALUES (100260, 'flied_dknbbe', 4, '2020-08-03 19:12:42', 1);
INSERT INTO `wk_crm_field_config` VALUES (100261, 'flied_jhsivt', 1, '2020-08-03 19:15:51', 2);
INSERT INTO `wk_crm_field_config` VALUES (100262, 'flied_bcethz', 3, '2020-08-04 17:17:04', 2);
INSERT INTO `wk_crm_field_config` VALUES (100263, 'flied_jeqgso', 1, '2020-08-04 17:17:04', 2);
INSERT INTO `wk_crm_field_config` VALUES (100264, 'flied_mjrdbe', 1, '2020-08-05 11:17:16', 2);
INSERT INTO `wk_crm_field_config` VALUES (100265, 'flied_mtfnrf', 1, '2020-08-05 11:17:17', 2);
INSERT INTO `wk_crm_field_config` VALUES (100266, 'flied_dlyjjb', 2, '2020-08-06 15:27:17', 1);
INSERT INTO `wk_crm_field_config` VALUES (100267, 'flied_wxpcbx', 3, '2020-08-06 15:27:18', 1);
INSERT INTO `wk_crm_field_config` VALUES (100268, 'flied_kjhmgc', 4, '2020-08-06 15:27:18', 2);
INSERT INTO `wk_crm_field_config` VALUES (100269, 'flied_gdcrxx', 2, '2020-08-06 15:27:18', 2);
INSERT INTO `wk_crm_field_config` VALUES (100270, 'flied_xfhonw', 1, '2020-08-06 15:27:18', 3);
INSERT INTO `wk_crm_field_config` VALUES (100271, 'flied_fdncyr', 2, '2020-08-06 15:27:18', 3);
INSERT INTO `wk_crm_field_config` VALUES (100272, 'flied_ijtnfc', 3, '2020-08-06 15:27:18', 3);
INSERT INTO `wk_crm_field_config` VALUES (100273, 'flied_wuggiv', 1, '2020-08-06 15:27:18', 4);
INSERT INTO `wk_crm_field_config` VALUES (100274, 'flied_mswlgq', 4, '2020-08-06 15:27:18', 4);
INSERT INTO `wk_crm_field_config` VALUES (100275, 'flied_nmkltw', 2, '2020-08-06 15:27:18', 4);
INSERT INTO `wk_crm_field_config` VALUES (100276, 'flied_jokwgt', 3, '2020-08-06 15:27:19', 4);
INSERT INTO `wk_crm_field_config` VALUES (100277, 'flied_drfhhl', 1, '2020-08-06 15:27:19', 5);
INSERT INTO `wk_crm_field_config` VALUES (100278, 'flied_uvqlpy', 4, '2020-08-06 15:27:19', 5);
INSERT INTO `wk_crm_field_config` VALUES (100279, 'flied_temgvq', 2, '2020-08-06 15:27:19', 5);
INSERT INTO `wk_crm_field_config` VALUES (100280, 'flied_lxujya', 3, '2020-08-06 15:27:19', 5);
INSERT INTO `wk_crm_field_config` VALUES (100281, 'flied_kixhfg', 1, '2020-08-06 15:27:19', 6);
INSERT INTO `wk_crm_field_config` VALUES (100282, 'flied_lzwnik', 2, '2020-08-06 15:27:19', 6);
INSERT INTO `wk_crm_field_config` VALUES (100283, 'flied_dununn', 3, '2020-08-06 15:27:19', 6);
INSERT INTO `wk_crm_field_config` VALUES (100284, 'flied_cqlfka', 1, '2020-08-06 15:27:19', 7);
INSERT INTO `wk_crm_field_config` VALUES (100285, 'flied_ylgnov', 4, '2020-08-06 15:27:19', 7);
INSERT INTO `wk_crm_field_config` VALUES (100286, 'flied_umnxvp', 2, '2020-08-06 15:27:19', 7);
INSERT INTO `wk_crm_field_config` VALUES (100287, 'flied_mhbkno', 3, '2020-08-06 15:27:19', 7);
INSERT INTO `wk_crm_field_config` VALUES (100288, 'flied_bthxmi', 1, '2020-08-06 15:27:20', 17);
INSERT INTO `wk_crm_field_config` VALUES (100289, 'flied_xqimlp', 4, '2020-08-06 15:27:20', 17);
INSERT INTO `wk_crm_field_config` VALUES (100290, 'flied_oojrlh', 2, '2020-08-06 15:27:20', 17);
INSERT INTO `wk_crm_field_config` VALUES (100291, 'flied_tmboyd', 3, '2020-08-06 15:27:20', 17);
INSERT INTO `wk_crm_field_config` VALUES (100292, 'flied_grasid', 1, '2020-08-12 18:14:51', 2);
INSERT INTO `wk_crm_field_config` VALUES (100293, 'flied_ilvojx', 1, '2020-08-19 17:17:04', 1);

-- ----------------------------
-- Table structure for wk_crm_field_extend
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_field_extend`;
CREATE TABLE `wk_crm_field_extend`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `parent_field_id` int(0) NOT NULL COMMENT 'Corresponding main field id',
  `field_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Custom field English identification',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Field Name',
  `type` int(0) NOT NULL DEFAULT 1 COMMENT 'Field Type 1 Single Line Text 2 Multi Line Text 3 Single Choice 4 Date 5 Number 6 Decimal 7 Mobile Phone 8 File 9 Multiple Choice 10 Person 11 Attachment 12 Department 13 Date Time 14 Email 15 Customer 16 Business Opportunity 17 Contact 18 Map 19 Product Type 20 Contract 21 Repayment plan',
  `remark` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field Description',
  `input_tips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Input hint',
  `max_length` int(0) NULL DEFAULT NULL COMMENT 'The maximum length',
  `default_value` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Defaults',
  `is_unique` int(0) NULL DEFAULT 0 COMMENT 'Is it unique 1 yes 0 no',
  `is_null` int(0) NULL DEFAULT 0 COMMENT 'Required 1 Yes 0 No',
  `sorting` int(0) NULL DEFAULT 1 COMMENT 'Sort from smallest to largest',
  `options` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'If the type is an option, it cannot be empty here. Multiple options are separated by',
  `operating` int(0) NULL DEFAULT 255 COMMENT 'Whether to allow editing',
  `is_hidden` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to hide 0 not to hide 1 to hide',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Last Modified',
  `field_type` int(0) NOT NULL DEFAULT 0 COMMENT 'Field source 0. Custom 1. Original fixed 2 Original field but the value exists in the extension table',
  `style_percent` int(0) NULL DEFAULT 50 COMMENT 'Style percentage %',
  `precisions` int(0) NULL DEFAULT NULL COMMENT 'Precision, maximum allowed decimal places',
  `form_position` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Form positioning Coordinate format: 1,1',
  `max_num_restrict` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Maximum value limit',
  `min_num_restrict` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'The minimum value of the limit',
  `form_assist_id` int(0) NULL DEFAULT NULL COMMENT 'Form auxiliary id, front-end generated',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `update_time`(`update_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12231 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Custom field table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_field_extend
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_field_sort
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_field_sort`;
CREATE TABLE `wk_crm_field_sort`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `field_id` int(0) NULL DEFAULT NULL COMMENT 'Field ID',
  `field_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field Name',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Name',
  `label` int(0) NOT NULL COMMENT 'Labels 1 Leads 2 Customers 3 Contacts 4 Products 5 Business Opportunities 6 Contracts 7 Repayment 8. Repayment Program',
  `type` int(0) NULL DEFAULT NULL COMMENT 'Field Type',
  `style` int(0) NULL DEFAULT NULL COMMENT 'Field width',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT 'Sort by field',
  `user_id` bigint(0) NOT NULL DEFAULT 0 COMMENT 'Userid',
  `is_hide` int(0) NOT NULL DEFAULT 1 COMMENT 'Whether to hide 0, not hide 1, hide',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `label`(`user_id`, `field_name`, `label`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1961 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Field sort table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_field_sort
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_instrument_sort
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_instrument_sort`;
CREATE TABLE `wk_crm_instrument_sort`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  `model_id` int(0) NOT NULL COMMENT 'Module id 1. Contract amount target and completion status 2. Data summary 3. Payment collection amount target and completion status 4. Performance indicator completion rate 5. Sales funnel 6. Forgotten reminder 7. Leaderboard',
  `list` int(0) NOT NULL COMMENT 'Column 1 Left 2 Right',
  `sort` int(0) NOT NULL COMMENT 'Sort',
  `is_hidden` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to hide 0 show 1 hide',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Dashboard Sort Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_instrument_sort
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_invoice
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_invoice`;
CREATE TABLE `wk_crm_invoice`  (
  `invoice_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Invoice id',
  `invoice_apply_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Invoice application number',
  `customer_id` int(0) NOT NULL COMMENT 'Customer id',
  `contract_id` int(0) NULL DEFAULT NULL COMMENT 'Contract id',
  `invoice_money` decimal(10, 2) NOT NULL COMMENT 'Invoice amount',
  `invoice_date` date NULL DEFAULT NULL COMMENT 'Billing date',
  `invoice_type` int(0) NOT NULL COMMENT 'Billing type',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `title_type` int(0) NULL DEFAULT NULL COMMENT 'Header type 1 unit 2 people',
  `invoice_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Billing heads',
  `tax_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Tax identification number',
  `deposit_bank` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Bank',
  `deposit_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Open an account',
  `deposit_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Billing address',
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Telephone',
  `contacts_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Contact name',
  `contacts_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Contact details',
  `contacts_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Mailing address',
  `examine_record_id` int(0) NULL DEFAULT NULL COMMENT 'Approval record id',
  `check_status` int(0) NULL DEFAULT NULL COMMENT 'Review status 0 pending review, 1 passed, 2 rejected, 3 reviewing, 4 withdrawn',
  `owner_user_id` bigint(0) NOT NULL COMMENT 'Person in charge id',
  `invoice_number` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Invoice number',
  `real_invoice_date` date NULL DEFAULT NULL COMMENT 'Actual Billing Date',
  `logistics_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Shipment number',
  `invoice_status` int(0) NOT NULL DEFAULT 0 COMMENT 'Billing status 0 not billed, 1 billed',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Batch id',
  PRIMARY KEY (`invoice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Invoice form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_invoice_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_invoice_data`;
CREATE TABLE `wk_crm_invoice_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2250 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Invoice Extended Fields Data Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_invoice_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_invoice_info
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_invoice_info`;
CREATE TABLE `wk_crm_invoice_info`  (
  `info_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Invoice information id',
  `customer_id` int(0) NOT NULL COMMENT 'Customer id',
  `title_type` int(0) NULL DEFAULT NULL COMMENT 'Header type 1 unit 2 people',
  `invoice_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Billing heads',
  `tax_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Tax identification number',
  `deposit_bank` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Bank',
  `deposit_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Open an account',
  `deposit_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Billing address',
  `telephone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Telephone',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Invoice Details' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_invoice_info
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_leads
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_leads`;
CREATE TABLE `wk_crm_leads`  (
  `leads_id` int(0) NOT NULL AUTO_INCREMENT,
  `is_transform` int(0) NULL DEFAULT 0 COMMENT '1 Converted 0 Not Converted',
  `followup` int(0) NULL DEFAULT NULL COMMENT 'Follow up status 0 not followed up 1 followed up',
  `leads_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Lead name',
  `customer_id` int(0) NULL DEFAULT NULL COMMENT 'Customer id',
  `next_time` datetime(0) NULL DEFAULT NULL COMMENT 'Next contact time',
  `telephone` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Telephone',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Phone number',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Mail',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Address',
  `remark` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT 'Remark',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Batches such as attachment batches',
  `is_receive` int(0) NULL DEFAULT NULL COMMENT '1 Assignment',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT 'Last follow-up time',
  `last_content` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Last follow up record',
  PRIMARY KEY (`leads_id`) USING BTREE,
  INDEX `owner_user_id`(`owner_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Cue sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_leads
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_leads_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_leads_data`;
CREATE TABLE `wk_crm_leads_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Lead custom field value table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_leads_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_leads_user_star
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_leads_user_star`;
CREATE TABLE `wk_crm_leads_user_star`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  `leads_id` int(0) NOT NULL COMMENT 'Customer id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `leads_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'User clue star relationship table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_leads_user_star
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_marketing
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_marketing`;
CREATE TABLE `wk_crm_marketing`  (
  `marketing_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Marketing id',
  `marketing_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Marketing name',
  `crm_type` int(0) NOT NULL DEFAULT 1 COMMENT '1 lead 2 client',
  `end_time` datetime(0) NOT NULL COMMENT 'Deadline',
  `relation_user_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Associate ID',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '1 enable 0 disable',
  `second` int(0) NOT NULL DEFAULT 0 COMMENT 'Each customer can only fill in the number of times 0 1',
  `field_data_id` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Fill out the fields for marketing content',
  `browse` int(0) NULL DEFAULT 0 COMMENT 'Viewing count',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NOT NULL COMMENT 'Change the time',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Starting time',
  `share_num` int(0) NULL DEFAULT 0 COMMENT 'Number of shares',
  `submit_num` int(0) NULL DEFAULT 0 COMMENT 'Number of commits',
  `synopsis` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Introduction',
  `main_file_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'First image id',
  `detail_file_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Event address',
  `marketing_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Type of activity',
  `marketing_money` decimal(11, 2) NULL DEFAULT NULL COMMENT 'Amount of activity',
  `relation_dept_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Associated department id',
  PRIMARY KEY (`marketing_id`) USING BTREE,
  INDEX `status`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Marketing Sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_marketing
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_marketing_field
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_marketing_field`;
CREATE TABLE `wk_crm_marketing_field`  (
  `field_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Custom field English identification',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Field Name',
  `type` int(0) NOT NULL DEFAULT 1 COMMENT 'Field Type 1 Single Line Text 2 Multi Line Text 3 Single Choice 4 Date 5 Number 6 Decimal 7 Mobile Phone 8 File 9 Multiple Choice 10 Person 11 Attachment 12 Department 13 Date Time 14 Email 15 Customer 16 Business Opportunity 17 Contact 18 Map 19 Product Type 20 Contract 21 Repayment plan',
  `remark` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field Description',
  `input_tips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Input hint',
  `max_length` int(0) NULL DEFAULT NULL COMMENT 'The maximum length',
  `default_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Defaults',
  `is_unique` int(0) NULL DEFAULT 0 COMMENT 'Is it unique 1 yes 0 no',
  `is_null` int(0) NULL DEFAULT 0 COMMENT 'Required 1 Yes 0 No',
  `sorting` int(0) NULL DEFAULT 1 COMMENT 'Sort from smallest to largest',
  `options` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'If the type is an option, it cannot be empty here. Multiple options are separated by',
  `operating` int(0) NULL DEFAULT 0 COMMENT 'Can delete and modify 0 modify and delete 1 modify 2 delete 3 none',
  `is_hidden` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to hide 0 not to hide 1 to hide',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Last Modified',
  `form_id` int(0) NULL DEFAULT NULL COMMENT 'FormId',
  `field_type` int(0) NOT NULL DEFAULT 0 COMMENT 'Field source 0. Custom 1. Original fixed 2 Original field but the value exists in the extension table',
  PRIMARY KEY (`field_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Campaign Fields Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_marketing_field
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_marketing_form
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_marketing_form`;
CREATE TABLE `wk_crm_marketing_form`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Name',
  `remarks` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Describe',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `status` int(0) NULL DEFAULT 1 COMMENT '1 to enable, 0 to disable',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `is_deleted` int(0) NULL DEFAULT 0 COMMENT '1 deleted',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT 'Delete time',
  `delete_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Delete person id',
  `update_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Campaign Form Information' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_marketing_form
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_marketing_info
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_marketing_info`;
CREATE TABLE `wk_crm_marketing_info`  (
  `r_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `marketing_id` int(0) NOT NULL COMMENT 'Link ID',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT '0 not synced 1 sync successful 2 sync failed',
  `field_info` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Marketing content fill in the field content',
  `device` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Device No',
  `owner_user_id` bigint(0) NOT NULL COMMENT 'Link ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Marketing Data Sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_marketing_info
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_number_setting
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_number_setting`;
CREATE TABLE `wk_crm_number_setting`  (
  `setting_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Set id',
  `pid` int(0) NOT NULL COMMENT 'Parent set id',
  `sort` int(0) NOT NULL COMMENT 'Numbering order',
  `type` int(0) NOT NULL COMMENT 'Number Type 1 Text 2 Date 3 Number',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Text content or date format or starting number',
  `increase_number` int(0) NULL DEFAULT NULL COMMENT 'Increment number',
  `reset_type` int(0) NULL DEFAULT NULL COMMENT 'Renumbering Cycle 1Daily 2Monthly 3Yearly 4Never',
  `last_number` int(0) NULL DEFAULT NULL COMMENT 'Last generated number',
  `last_date` date NULL DEFAULT NULL COMMENT 'Last generated time',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator id',
  PRIMARY KEY (`setting_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'The system automatically generates a number setting table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_number_setting
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_owner_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_owner_record`;
CREATE TABLE `wk_crm_owner_record`  (
  `record_id` int(0) NOT NULL AUTO_INCREMENT,
  `type_id` int(0) NOT NULL COMMENT 'Object id',
  `type` int(0) NOT NULL COMMENT 'Object type',
  `pre_owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Previous responsible person',
  `post_owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Take over the person in charge',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Responsible Person Change Record Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_owner_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_print_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_print_record`;
CREATE TABLE `wk_crm_print_record`  (
  `record_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Record id',
  `crm_type` int(0) NOT NULL,
  `type_id` int(0) NOT NULL,
  `template_id` int(0) NOT NULL COMMENT 'Template id',
  `record_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Print record',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Print record sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_print_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_print_template
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_print_template`;
CREATE TABLE `wk_crm_print_template`  (
  `template_id` int(0) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Template name',
  `type` int(0) NOT NULL COMMENT 'Associated object',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Template',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  PRIMARY KEY (`template_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Print template sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_print_template
-- ----------------------------
INSERT INTO `wk_crm_print_template` VALUES (20, 'Contract terms print template', 6, 'Translation', 0, '2020-08-22 11:40:42', NULL);
INSERT INTO `wk_crm_print_template` VALUES (21, 'Contract Order Print Template', 6, 'Translation', 0, '2020-08-22 11:40:42', NULL);
INSERT INTO `wk_crm_print_template` VALUES (22, 'Business Opportunity Print Template', 5, 'Translation', 0, '2020-08-22 11:40:42', NULL);
INSERT INTO `wk_crm_print_template` VALUES (23, 'Receipt print template', 7, 'A-wk-tag=\"receivables.return_type\"><span style=\"font-family: simsun, serif; font-size: 14px;\">{回款方式}</span></span></td></tr><tr style=\"height: 18px;\"><td style=\"width: 50%; height: 18px;\"><span style=\"font-family: simsun, serif; font-size: 14px;\">回款期数：</span><span class=\"wk-print-tag-wukong wk-tiny-color--receivables\" contenteditable=\"true\" data-wk-tag=\"receivables.plan_num\"><span style=\"font-family: simsun, serif; font-size: 14px;\">{期数}</span></span></td><td style=\"width: 50%; height: 18px;\"><span style=\"font-family: simsun, serif; font-size: 14px;\">回款金额（元）：</span><span class=\"wk-print-tag-wukong wk-tiny-color--receivables\" contenteditable=\"true\" data-wk-tag=\"receivables.money\"><span style=\"font-family: simsun, serif; font-size: 14px;\">{回款金额}</span></span></td></tr><tr style=\"height: 18px;\"><td style=\"width: 50%; height: 18px;\"><span style=\"font-family: simsun, serif; font-size: 14px;\">负责人：</span><span class=\"wk-print-tag-wukong wk-tiny-color--receivables\" contenteditable=\"true\" data-wk-tag=\"receivables.owner_user_name\"><span style=\"font-family: simsun, serif; font-size: 14px;\">{负责人}</span></span></td><td style=\"width: 50%; height: 18px;\">&nbsp;</td></tr><tr style=\"height: 18px;\"><td style=\"width: 50%; height: 18px;\" colspan=\"2\"><span style=\"font-family: simsun, serif; font-size: 14px;\">备注：</span><span class=\"wk-print-tag-wukong wk-tiny-color--receivables\" contenteditable=\"true\" data-wk-tag=\"receivables.remark\"><span style=\"font-family: simsun, serif; font-size: 14px;\">{备注}</span></span></td></tr></tbody></table>', 0, '2020-08-22 11:40:42', NULL);

-- ----------------------------
-- Table structure for wk_crm_product
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_product`;
CREATE TABLE `wk_crm_product`  (
  `product_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Product name',
  `num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Product code',
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Unit',
  `price` decimal(18, 2) NULL DEFAULT 0.00 COMMENT 'Price',
  `status` int(0) NULL DEFAULT NULL COMMENT 'Status 1 Listing 0 Delisting 3 Deleting',
  `category_id` int(0) NULL DEFAULT NULL COMMENT 'Product Category ID',
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Product Description',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Batch',
  `old_product_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_product
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_product_category
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_product_category`;
CREATE TABLE `wk_crm_product_category`  (
  `category_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `pid` int(0) NULL DEFAULT 0,
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14768 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Product classification table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_product_category
-- ----------------------------
INSERT INTO `wk_crm_product_category` VALUES (14767, 'Default', 0);

-- ----------------------------
-- Table structure for wk_crm_product_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_product_data`;
CREATE TABLE `wk_crm_product_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Product custom field value table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_product_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_product_detail_img
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_product_detail_img`;
CREATE TABLE `wk_crm_product_detail_img`  (
  `img_id` int(0) NOT NULL AUTO_INCREMENT,
  `product_id` int(0) NULL DEFAULT NULL COMMENT 'Product id',
  `remarks` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `main_file_ids` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Main image',
  `detail_file_ids` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`img_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Product Detail Image' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_product_detail_img
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_product_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_product_user`;
CREATE TABLE `wk_crm_product_user`  (
  `product_user_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `product_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`product_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'The Product Staff applet displays the association table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_product_user
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_receivables
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_receivables`;
CREATE TABLE `wk_crm_receivables`  (
  `receivables_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Payment ID',
  `number` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Payment number',
  `receivables_plan_id` int(0) NULL DEFAULT NULL COMMENT 'Repayment plan ID',
  `customer_id` int(0) NULL DEFAULT NULL COMMENT 'Customer ID',
  `contract_id` int(0) NULL DEFAULT NULL COMMENT 'Contract ID',
  `check_status` int(0) NULL DEFAULT NULL COMMENT '0 pending review, 1 passed, 2 rejected, 3 under review 4: withdrawn 5 not submitted',
  `examine_record_id` int(0) NULL DEFAULT NULL COMMENT 'Audit record ID',
  `return_time` date NULL DEFAULT NULL COMMENT 'Payment date',
  `return_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Payment method',
  `money` decimal(17, 2) NULL DEFAULT NULL COMMENT 'Repayment amount',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Batch',
  PRIMARY KEY (`receivables_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Payment form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_receivables
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_receivables_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_receivables_data`;
CREATE TABLE `wk_crm_receivables_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Receipt custom field value table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_receivables_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_receivables_plan
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_receivables_plan`;
CREATE TABLE `wk_crm_receivables_plan`  (
  `receivables_plan_id` int(0) NOT NULL AUTO_INCREMENT,
  `num` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Period',
  `receivables_id` int(0) NULL DEFAULT NULL COMMENT 'Payment ID',
  `status` int(0) NULL DEFAULT NULL COMMENT '1 completed 0 not completed',
  `money` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Planned repayment amount',
  `return_date` datetime(0) NULL DEFAULT NULL COMMENT 'Scheduled payment date',
  `return_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Plan payment method',
  `remind` int(0) NULL DEFAULT NULL COMMENT 'Reminder a few days in advance',
  `remind_date` datetime(0) NULL DEFAULT NULL COMMENT 'Reminder date',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Attachment batch ID',
  `real_received_money` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Actual repayment amount',
  `real_return_date` datetime(0) NULL DEFAULT NULL COMMENT 'Actual payment date',
  `unreceived_money` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Unpaid amount',
  `received_status` int(0) NULL DEFAULT 0 COMMENT 'Payment status 0 Payment pending 1 Payment completed 2 Partial payment 3 Void 4 Overdue 5 To be effective',
  `contract_id` int(0) NOT NULL COMMENT 'Contract ID',
  `customer_id` int(0) NULL DEFAULT NULL COMMENT 'Customer ID',
  PRIMARY KEY (`receivables_plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Repayment schedule' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_receivables_plan
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_receivables_plan_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_receivables_plan_data`;
CREATE TABLE `wk_crm_receivables_plan_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE,
  INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Repayment plan custom field value table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_receivables_plan_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_return_visit
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_return_visit`;
CREATE TABLE `wk_crm_return_visit`  (
  `visit_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Return visit id',
  `visit_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Return visit number',
  `visit_time` datetime(0) NULL DEFAULT NULL COMMENT 'Return visit time',
  `owner_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Return visitor id',
  `customer_id` int(0) NULL DEFAULT NULL COMMENT 'Customer id',
  `contract_id` int(0) NULL DEFAULT NULL COMMENT 'Contract id',
  `contacts_id` int(0) NULL DEFAULT NULL COMMENT 'Contact id',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NOT NULL COMMENT 'Update time',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Batch id',
  PRIMARY KEY (`visit_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Return form\r\n' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_return_visit
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_return_visit_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_return_visit_data`;
CREATE TABLE `wk_crm_return_visit_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Return to Extended Data Sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_return_visit_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_role_field
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_role_field`;
CREATE TABLE `wk_crm_role_field`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_id` int(0) NOT NULL COMMENT 'Character id',
  `label` int(0) NOT NULL COMMENT 'Crm module',
  `field_id` int(0) NULL DEFAULT NULL COMMENT 'Field id',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field Name',
  `auth_level` int(0) NOT NULL COMMENT 'Permissions 1 can not be edited but not viewed 2 can be viewed but not edited 3 can be edited and viewed',
  `operate_type` int(0) NOT NULL COMMENT 'Operation permissions 1 can be set 2 only view permissions can be set 3 only edit permissions can be set 4 can not be set',
  `mask_type` int(0) NULL DEFAULT 0 COMMENT 'Mask type 0 hide none 1 list hide details not hide 2 hide both',
  `field_type` int(0) NULL DEFAULT NULL COMMENT '0 custom field 1 original field 2 original field but the value is in the data table 3 related table field 4 system field',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Role Field Authorization Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_role_field
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_scene
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_scene`;
CREATE TABLE `wk_crm_scene`  (
  `scene_id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NOT NULL COMMENT 'Classification',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Scene name',
  `user_id` bigint(0) NOT NULL COMMENT 'User ID',
  `sort` int(0) NOT NULL COMMENT 'Sort ID',
  `data` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Attribute value',
  `is_hide` int(0) NOT NULL COMMENT '1 hide',
  `is_system` int(0) NOT NULL COMMENT '1 system 0 custom',
  `bydata` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'System parameters',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  PRIMARY KEY (`scene_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 621 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Scenes' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_scene
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_scene_default
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_scene_default`;
CREATE TABLE `wk_crm_scene_default`  (
  `default_id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NOT NULL COMMENT 'Type',
  `user_id` bigint(0) NOT NULL COMMENT 'Person ID',
  `scene_id` int(0) NOT NULL COMMENT 'Scene ID',
  PRIMARY KEY (`default_id`) USING BTREE,
  UNIQUE INDEX `default_id`(`default_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Scene default relationship table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_scene_default
-- ----------------------------

-- ----------------------------
-- Table structure for wk_crm_team_members
-- ----------------------------
DROP TABLE IF EXISTS `wk_crm_team_members`;
CREATE TABLE `wk_crm_team_members`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NOT NULL COMMENT 'Type, same as crm type',
  `type_id` int(0) NOT NULL COMMENT 'Corresponding type primary key ID',
  `user_id` bigint(0) NOT NULL COMMENT 'User ID',
  `power` int(0) NULL DEFAULT NULL COMMENT '1 Read-only 2 Read-write 3 Responsible',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `expires_time` datetime(0) NULL DEFAULT NULL COMMENT 'Expiration',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `type`(`type`, `type_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 348 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Crm team member table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_crm_team_members
-- ----------------------------

-- ----------------------------
-- Table structure for wk_email_account
-- ----------------------------
DROP TABLE IF EXISTS `wk_email_account`;
CREATE TABLE `wk_email_account`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `email_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Email address',
  `email_password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Email Password',
  `send_nick` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Send nickname',
  `configuration_mode` int(0) NULL DEFAULT 1 COMMENT 'Mailbox configuration method 1: Automatic configuration 2: Manual configuration',
  `service_type` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Type of receiving service: POP3, IMAP',
  `receiving_server` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Incoming server address',
  `is_receiving` int(0) NULL DEFAULT 1 COMMENT 'Whether the receiving server enables ssl proxy 0: not enabled 1: enabled',
  `receiving_ssl` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Incoming server SSL port',
  `smtp_server` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'SMTP server',
  `is_smtp` int(0) NULL DEFAULT 1 COMMENT 'Whether the smtp server enables ssl proxy 0: not enabled 1: enabled',
  `smtp_ssl` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Smtp port number',
  `signature` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT 'Signature',
  `email_count` int(0) NULL DEFAULT NULL COMMENT 'Total number of emails',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Email address' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_email_account
-- ----------------------------

-- ----------------------------
-- Table structure for wk_email_file
-- ----------------------------
DROP TABLE IF EXISTS `wk_email_file`;
CREATE TABLE `wk_email_file`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'File name',
  `file_size` bigint(0) NULL DEFAULT NULL COMMENT 'File size',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Founder',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'File batch id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `batch_id`(`batch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Mailbox attachment name table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_email_file
-- ----------------------------

-- ----------------------------
-- Table structure for wk_email_lately
-- ----------------------------
DROP TABLE IF EXISTS `wk_email_lately`;
CREATE TABLE `wk_email_lately`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `customer_id` int(0) NULL DEFAULT NULL COMMENT 'When the customer id is empty, it means it is not a list customer. When it is not empty, it means a customer',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `email` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Mail',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Change the time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Founder',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Email recent contacts' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_email_lately
-- ----------------------------

-- ----------------------------
-- Table structure for wk_email_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_email_record`;
CREATE TABLE `wk_email_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `email_account_id` int(0) NULL DEFAULT NULL COMMENT 'Send account id',
  `sender` varchar(225) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Sender nickname',
  `sender_email` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT 'Sender email',
  `receipt_name` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `receipt_emails` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT 'Collection of recipient mailboxes comma-separated',
  `cc_name` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `cc_emails` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT 'Cc emails comma separated',
  `theme` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Theme',
  `attachment` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Appendix',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT 'Content',
  `email_type` int(0) NULL DEFAULT NULL COMMENT 'Mailbox Type 1: Inbox 2: Outbox 3: Drafts 4: Delete 5: Trash',
  `is_read` int(0) NULL DEFAULT 0 COMMENT 'Whether it has been read 0: unread 1: read',
  `is_start` int(0) NULL DEFAULT 0 COMMENT 'Whether starred 0: not starred 1: starred',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `record_type` int(0) NULL DEFAULT NULL COMMENT 'Record Type 1: Send 2: Save Draft 3: Delete Mail 4: Star Mail 5: Spam',
  `message_id` int(0) NULL DEFAULT NULL COMMENT 'Email id',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `is_del` int(0) NULL DEFAULT 0 COMMENT '0: Not deleted 1: Deleted',
  `email_uid` bigint(0) NULL DEFAULT NULL COMMENT 'Mailuid',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Mail record' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_email_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_examine
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine`;
CREATE TABLE `wk_examine`  (
  `examine_id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Approval ID',
  `examine_init_id` bigint(0) NULL DEFAULT NULL COMMENT 'Approval Initialization Id',
  `label` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '0 OA 1 Contract 2 Receipt 3 Invoice 4 Salary 5 Purchase Review 6 Purchase Return Review 7 Sales Review 8 Sales Return Review 9 Payment Slip Review 10 Payment Slip Review 11 Inventory Review 12 Transfer Review',
  `examine_icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Icon',
  `examine_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Approval name',
  `recheck_type` int(0) NULL DEFAULT NULL COMMENT 'Re-review actions after withdrawal 1 from the first level 2 from the rejected level',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Founder',
  `status` int(0) NULL DEFAULT NULL COMMENT '1 normal 2 disabled 3 deleted',
  `batch_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Batch ID',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Remark',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Change the time',
  `update_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Modified by',
  `user_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Visible range (employees)',
  `dept_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Visible range (department)',
  `oa_type` int(0) NULL DEFAULT 0 COMMENT '1 General Approval 2 Leave Approval 3 Business Trip Approval 4 Overtime Approval 5 Travel Reimbursement 6 Loan Application 0 Custom Approval',
  PRIMARY KEY (`examine_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1164178 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Approval Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine
-- ----------------------------
INSERT INTO `wk_examine` VALUES (25375, 25375, 2, NULL, 'Payment Approval Process', 1, NULL, 3, 1, '38e4ecd1525111ebbe7418c04d26d688', '', '2021-05-07 14:00:50', 3, NULL, NULL, 0);
INSERT INTO `wk_examine` VALUES (25376, 25376, 1, NULL, 'Contract approval process', 1, NULL, 3, 1, '38e4f6e4525111ebbe7418c04d26d688', 'Illustrate', '2021-05-07 14:00:50', 3, NULL, NULL, 0);
INSERT INTO `wk_examine` VALUES (25377, 25377, 3, NULL, 'Invoice Approval Process', 1, NULL, 0, 1, '38e4f798525111ebbe7418c04d26d688', '', '2021-05-07 14:00:50', 0, NULL, NULL, 0);
INSERT INTO `wk_examine` VALUES (1072979, 1072979, 0, 'wk wk-l-record,#3ABCFB', 'General approval', 1, '2019-04-26 15:06:34', 3, 1, '38efbcd2525111ebbe7418c04d26d688', 'General approval', '2021-05-07 14:00:50', 3, '', '', 1);
INSERT INTO `wk_examine` VALUES (1072980, 1072980, 0, 'wk wk-leave,#00CAAB', 'Leave approval', 1, '2019-04-17 18:52:44', 3, 1, '38efbdd4525111ebbe7418c04d26d688', 'Leave approval', '2021-05-07 14:00:50', 3, '', '', 2);
INSERT INTO `wk_examine` VALUES (1072981, 1072981, 0, 'wk wk-trip,#3ABCFB', 'Travel approval', 1, '2019-04-17 18:52:50', 3, 1, '38efbe57525111ebbe7418c04d26d688', 'Travel approval', '2021-05-07 14:00:50', 3, '', '', 3);
INSERT INTO `wk_examine` VALUES (1072982, 1072982, 0, 'wk wk-overtime,#FAAD14', 'Overtime approval', 1, '2019-04-17 18:52:59', 3, 1, '38efbe9f525111ebbe7418c04d26d688', 'Overtime approval', '2021-05-07 14:00:50', 3, '', '', 4);
INSERT INTO `wk_examine` VALUES (1072983, 1072983, 0, 'wk wk-reimbursement,#3ABCFB', 'Travel reimbursement', 1, '2019-04-17 18:53:13', 3, 1, '38efbee2525111ebbe7418c04d26d688', 'Travel reimbursement', '2021-05-07 14:00:50', 3, '', '', 5);
INSERT INTO `wk_examine` VALUES (1072984, 1072984, 0, 'wk wk-go-out,#FF6033', 'Loan application', 1, '2019-04-17 18:54:44', 3, 1, '38efbf24525111ebbe7418c04d26d688', 'Loan application', '2021-05-07 14:00:50', 3, '', '', 6);

-- ----------------------------
-- Table structure for wk_examine_condition
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_condition`;
CREATE TABLE `wk_examine_condition`  (
  `condition_id` int(0) NOT NULL AUTO_INCREMENT,
  `condition_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Condition name',
  `flow_id` int(0) NOT NULL COMMENT 'Approval Process ID',
  `priority` int(0) NOT NULL COMMENT 'Priority The lower the number, the higher the priority',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `batch_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Batch ID',
  PRIMARY KEY (`condition_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1490 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Approval condition table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_condition
-- ----------------------------

-- ----------------------------
-- Table structure for wk_examine_condition_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_condition_data`;
CREATE TABLE `wk_examine_condition_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `condition_id` int(0) NOT NULL COMMENT 'Condition ID',
  `flow_id` int(0) NOT NULL COMMENT 'Approval Process ID',
  `field_id` int(0) NULL DEFAULT NULL COMMENT 'Field ID',
  `field_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Field Name',
  `condition_type` int(0) NULL DEFAULT NULL COMMENT 'Join Condition 1 equal to 2 greater than 3 less than 4 greater than or equal to 5 less than or equal to 6 between 7 contains 8 employees 9 departments 10 roles',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Value, json array format',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Batch ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Field Korean name',
  `type` int(0) NULL DEFAULT NULL COMMENT 'Field Type 1 Single Line Text 2 Multi Line Text 3 Single Choice 4 Date 5 Number 6 Decimal 7 Mobile Phone 8 File 9 Multiple Choice 10 Person 11 Attachment 12 Department 13 Date Time 14 Email 15 Customer 16 Business Opportunity 17 Contact 18 Map 19 Product Type 20 Contract 21 Repayment plan',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1506 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Approval Condition Extended Field Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_condition_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_examine_flow
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_flow`;
CREATE TABLE `wk_examine_flow`  (
  `flow_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Review Process ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Name',
  `examine_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT 'Approval ID',
  `examine_type` int(0) NOT NULL COMMENT '0 Condition 1 Designated Member 2 Supervisor 3 Role 4 Sponsor\'s Choice 5 Consecutive Multiple Supervisors',
  `examine_error_handling` int(0) NOT NULL DEFAULT 1 COMMENT 'What to do when the approval cannot find the user or the conditions are not met 1 Automatically pass 2 Admin approval',
  `condition_id` int(0) NOT NULL DEFAULT 0 COMMENT 'Condition ID',
  `sort` int(0) NOT NULL COMMENT 'Execution order, not nullable',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'User ID',
  `batch_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Batch ID',
  PRIMARY KEY (`flow_id`) USING BTREE,
  INDEX `examine_id`(`examine_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1163342 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Approval process table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_flow
-- ----------------------------
INSERT INTO `wk_examine_flow` VALUES (1163333, 'Business Approval 6375', 25375, 4, 2, 0, 1, NULL, 3, '38e4ecd1525111ebbe7418c04d26d688');
INSERT INTO `wk_examine_flow` VALUES (1163334, 'Business Approval 3841', 25376, 4, 2, 0, 1, NULL, 3, '38e4f6e4525111ebbe7418c04d26d688');
INSERT INTO `wk_examine_flow` VALUES (1163335, 'Business Approval 3682', 25377, 4, 2, 0, 1, NULL, 0, '38e4f798525111ebbe7418c04d26d688');
INSERT INTO `wk_examine_flow` VALUES (1163336, 'Office Approval 3621', 1072979, 4, 2, 0, 1, '2019-04-26 15:06:34', 3, '38efbcd2525111ebbe7418c04d26d688');
INSERT INTO `wk_examine_flow` VALUES (1163337, 'Office Approval 1325', 1072980, 4, 2, 0, 1, '2019-04-17 18:52:44', 3, '38efbdd4525111ebbe7418c04d26d688');
INSERT INTO `wk_examine_flow` VALUES (1163338, 'Office approval 6681', 1072981, 4, 2, 0, 1, '2019-04-17 18:52:50', 3, '38efbe57525111ebbe7418c04d26d688');
INSERT INTO `wk_examine_flow` VALUES (1163339, 'Office Approval 2494', 1072982, 4, 2, 0, 1, '2019-04-17 18:52:59', 3, '38efbe9f525111ebbe7418c04d26d688');
INSERT INTO `wk_examine_flow` VALUES (1163340, 'Office approval 8739', 1072983, 4, 2, 0, 1, '2019-04-17 18:53:13', 3, '38efbee2525111ebbe7418c04d26d688');
INSERT INTO `wk_examine_flow` VALUES (1163341, 'Office approval 8180', 1072984, 4, 2, 0, 1, '2019-04-17 18:54:44', 3, '38efbf24525111ebbe7418c04d26d688');

-- ----------------------------
-- Table structure for wk_examine_flow_continuous_superior
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_flow_continuous_superior`;
CREATE TABLE `wk_examine_flow_continuous_superior`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `flow_id` int(0) NOT NULL COMMENT 'Approval Process ID',
  `role_id` int(0) NULL DEFAULT NULL COMMENT 'Role id',
  `max_level` int(0) NULL DEFAULT NULL COMMENT 'The highest level of role approval or the Nth level of the organizational structure',
  `type` int(0) NULL DEFAULT NULL COMMENT '1 Assign role 2 Top level of organizational structure',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Batch ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 143 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Approval Process Continuous Multi-level Supervisor Approval Record Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_flow_continuous_superior
-- ----------------------------

-- ----------------------------
-- Table structure for wk_examine_flow_member
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_flow_member`;
CREATE TABLE `wk_examine_flow_member`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `flow_id` int(0) NOT NULL COMMENT 'Approval Process ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Approver ID',
  `type` int(0) NULL DEFAULT NULL COMMENT '1 Approve in turn 2 Countersign 3 Or sign',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT 'Collation',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Batch ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4448 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Approval Process Designated Member Record Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_flow_member
-- ----------------------------

-- ----------------------------
-- Table structure for wk_examine_flow_optional
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_flow_optional`;
CREATE TABLE `wk_examine_flow_optional`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `flow_id` int(0) NOT NULL COMMENT 'Review Process ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Approver ID',
  `role_id` int(0) NULL DEFAULT NULL COMMENT 'Role id',
  `choose_type` int(0) NULL DEFAULT NULL COMMENT 'Choice type 1 Choose one person 2 Choose multiple people',
  `type` int(0) NULL DEFAULT NULL COMMENT '1 Approve in turn 2 Countersign 3 Or sign',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT 'Collation',
  `batch_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Batch ID',
  `range_type` int(0) NULL DEFAULT NULL COMMENT 'Scope of selection 1 Company-wide 2 Designated members 3 Designated roles',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `flow_id`(`flow_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1313998 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Approval Process Optional Member Record Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_flow_optional
-- ----------------------------
INSERT INTO `wk_examine_flow_optional` VALUES (1313980, 1163333, NULL, NULL, 2, 1, 0, '38e4ecd1525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313981, 1163334, NULL, NULL, 2, 1, 0, '38e4f6e4525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313982, 1163335, NULL, NULL, 2, 1, 0, '38e4f798525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313983, 1163333, NULL, NULL, 2, 1, 0, '38e4ecd1525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313984, 1163334, NULL, NULL, 2, 1, 0, '38e4f6e4525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313985, 1163335, NULL, NULL, 2, 1, 0, '38e4f798525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313986, 1163336, NULL, NULL, 2, 1, 0, '38efbcd2525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313987, 1163337, NULL, NULL, 2, 1, 0, '38efbdd4525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313988, 1163338, NULL, NULL, 2, 1, 0, '38efbe57525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313989, 1163339, NULL, NULL, 2, 1, 0, '38efbe9f525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313990, 1163340, NULL, NULL, 2, 1, 0, '38efbee2525111ebbe7418c04d26d688', 1);
INSERT INTO `wk_examine_flow_optional` VALUES (1313991, 1163341, NULL, NULL, 2, 1, 0, '38efbf24525111ebbe7418c04d26d688', 1);

-- ----------------------------
-- Table structure for wk_examine_flow_role
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_flow_role`;
CREATE TABLE `wk_examine_flow_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `flow_id` int(0) NOT NULL COMMENT 'Review Process ID',
  `role_id` int(0) NULL DEFAULT NULL COMMENT 'Role id',
  `type` int(0) NULL DEFAULT NULL COMMENT '2 countersign 3 or sign',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Batch ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 311 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Approval Process Role Approval Record Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_flow_role
-- ----------------------------

-- ----------------------------
-- Table structure for wk_examine_flow_superior
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_flow_superior`;
CREATE TABLE `wk_examine_flow_superior`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `flow_id` int(0) NOT NULL COMMENT 'Review Process ID',
  `parent_level` int(0) NULL DEFAULT NULL COMMENT 'Direct superior level 1 represents the direct superior 2 represents the direct superior\'s superior',
  `type` int(0) NULL DEFAULT NULL COMMENT 'When the superior cannot be found, whether to approve the approval by the superior superior 0 No 1 Yes',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Batch ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2478 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Approval Process Supervisor Approval Record Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_flow_superior
-- ----------------------------

-- ----------------------------
-- Table structure for wk_examine_manager_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_manager_user`;
CREATE TABLE `wk_examine_manager_user`  (
  `id` int(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `examine_id` bigint(0) UNSIGNED NOT NULL COMMENT 'Approval ID',
  `user_id` bigint(0) NOT NULL COMMENT 'Admin ID',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT 'From small to large',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `examine_id`(`examine_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 527852 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Approval Administrator Settings Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_manager_user
-- ----------------------------
INSERT INTO `wk_examine_manager_user` VALUES (527837, 25375, 14773, 0);
INSERT INTO `wk_examine_manager_user` VALUES (527838, 25376, 14773, 0);
INSERT INTO `wk_examine_manager_user` VALUES (527839, 25377, 14773, 0);
INSERT INTO `wk_examine_manager_user` VALUES (527840, 1072979, 14773, 0);
INSERT INTO `wk_examine_manager_user` VALUES (527841, 1072980, 14773, 0);
INSERT INTO `wk_examine_manager_user` VALUES (527842, 1072981, 14773, 0);
INSERT INTO `wk_examine_manager_user` VALUES (527843, 1072982, 14773, 0);
INSERT INTO `wk_examine_manager_user` VALUES (527844, 1072983, 14773, 0);
INSERT INTO `wk_examine_manager_user` VALUES (527845, 1072984, 14773, 0);

-- ----------------------------
-- Table structure for wk_examine_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_record`;
CREATE TABLE `wk_examine_record`  (
  `record_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Audit record ID',
  `examine_id` bigint(0) NOT NULL COMMENT 'Audit ID',
  `label` int(0) NULL DEFAULT NULL COMMENT 'Business type',
  `flow_id` int(0) NOT NULL COMMENT 'Process ID',
  `type_id` int(0) NULL DEFAULT NULL COMMENT 'Associated business primary key ID',
  `examine_status` int(0) NULL DEFAULT NULL COMMENT 'Review status 0 Not reviewed 1 Review passed 2 Review rejected 3 Reviewed 4 Withdrawn',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Founder',
  `update_time` datetime(0) NOT NULL COMMENT 'Change the time',
  `update_user_id` bigint(0) NOT NULL COMMENT 'Modified by',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1004974 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Audit record sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_examine_record_log
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_record_log`;
CREATE TABLE `wk_examine_record_log`  (
  `log_id` int(0) NOT NULL AUTO_INCREMENT,
  `examine_id` bigint(0) NOT NULL COMMENT 'Approval ID',
  `flow_id` int(0) NOT NULL COMMENT 'Approval Process ID',
  `record_id` int(0) NOT NULL COMMENT 'Approval record ID',
  `type` int(0) NULL DEFAULT NULL COMMENT '1 Approve in turn 2 Countersign 3 Or sign',
  `sort` int(0) NULL DEFAULT NULL COMMENT 'Sort',
  `examine_status` int(0) NOT NULL COMMENT 'Review status 0 pending review, 1 passed, 2 rejected, 3 reviewing 4: withdrawn 5 unsubmitted 6 created 7 deleted 8 void',
  `examine_user_id` bigint(0) NULL DEFAULT 0 COMMENT 'Reviewer ID',
  `examine_role_id` int(0) NULL DEFAULT 0 COMMENT 'Audit role ID',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Change the time',
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Batch ID',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Review Notes',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1137829 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Audit log table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_record_log
-- ----------------------------

-- ----------------------------
-- Table structure for wk_examine_record_optional
-- ----------------------------
DROP TABLE IF EXISTS `wk_examine_record_optional`;
CREATE TABLE `wk_examine_record_optional`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `flow_id` int(0) NOT NULL COMMENT 'Process ID',
  `record_id` int(0) NOT NULL COMMENT 'Audit record ID',
  `user_id` bigint(0) NOT NULL COMMENT 'User ID',
  `sort` int(0) NOT NULL DEFAULT 1 COMMENT 'Sort. From small to large',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 326 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Review the self-selected member selection member form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_examine_record_optional
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_action_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_action_record`;
CREATE TABLE `wk_km_action_record`  (
  `record_id` int(0) NOT NULL AUTO_INCREMENT,
  `status` int(0) NULL DEFAULT NULL COMMENT '1 browse 2 delete',
  `type` int(0) NOT NULL COMMENT '1 Knowledge Base 2 Folders 3 Documents 4 Files',
  `type_id` int(0) NOT NULL,
  `create_user_id` bigint(0) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Knowledge base operation records (most recently used)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_action_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_auth
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_auth`;
CREATE TABLE `wk_km_auth`  (
  `auth_id` int(0) NOT NULL AUTO_INCREMENT,
  `is_open` int(0) NULL DEFAULT NULL COMMENT 'Is it public 0 private 1 public',
  `open_auth` int(0) NULL DEFAULT NULL COMMENT 'Public permissions 2 can be edited 3 are visible, not editable',
  PRIMARY KEY (`auth_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 92 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Documents folder permission table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_auth
-- ----------------------------
INSERT INTO `wk_km_auth` VALUES (87, 1, 2);
INSERT INTO `wk_km_auth` VALUES (88, 1, 2);
INSERT INTO `wk_km_auth` VALUES (89, 1, 2);
INSERT INTO `wk_km_auth` VALUES (90, 1, 2);
INSERT INTO `wk_km_auth` VALUES (91, 1, 2);

-- ----------------------------
-- Table structure for wk_km_auth_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_auth_user`;
CREATE TABLE `wk_km_auth_user`  (
  `r_id` int(0) NOT NULL AUTO_INCREMENT,
  `auth_id` int(0) NULL DEFAULT NULL,
  `user_id` bigint(0) NULL DEFAULT NULL,
  `auth` int(0) NULL DEFAULT NULL COMMENT 'Private permissions 1 All permissions 2 Edit permissions 3 Read only permissions',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 147 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Knowledge base permission user association table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_auth_user
-- ----------------------------
INSERT INTO `wk_km_auth_user` VALUES (142, 87, 14773, 1, '2020-08-22 16:11:27', 14773);
INSERT INTO `wk_km_auth_user` VALUES (143, 88, 14773, 1, '2020-08-22 16:11:27', 14773);
INSERT INTO `wk_km_auth_user` VALUES (144, 89, 14773, 1, '2020-08-22 16:11:27', 14773);
INSERT INTO `wk_km_auth_user` VALUES (145, 90, 14773, 1, '2020-08-22 16:11:27', 14773);
INSERT INTO `wk_km_auth_user` VALUES (146, 91, 14773, 1, '2020-08-22 16:11:27', 14773);

-- ----------------------------
-- Table structure for wk_km_collect
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_collect`;
CREATE TABLE `wk_km_collect`  (
  `collect_id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NOT NULL COMMENT '1 Knowledge Base 2 Folders 3 Files',
  `type_id` int(0) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Knowledge Base Collection Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_collect
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_document
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_document`;
CREATE TABLE `wk_km_document`  (
  `document_id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Document title',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL,
  `type` int(0) NOT NULL COMMENT '3 rich text 4 files',
  `parent_id` int(0) NULL DEFAULT 0,
  `status` int(0) NULL DEFAULT 1 COMMENT '-1 delete 0 draft 1 normal 2 template',
  `library_id` int(0) NULL DEFAULT NULL,
  `folder_id` int(0) NOT NULL COMMENT 'Folder id',
  `auth_id` int(0) NULL DEFAULT NULL,
  `label_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Tag id',
  `create_time` datetime(0) NOT NULL,
  `create_user_id` bigint(0) NOT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `delete_user_id` bigint(0) NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`document_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Knowledge Base Document Sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_document
-- ----------------------------
INSERT INTO `wk_km_document` VALUES (85, 'Product requirements document', 'Translation', 3, 0, 1, 25, 0, 87, NULL, '2020-08-22 16:11:27', 14773, '2020-08-22 16:11:27', NULL, NULL);
INSERT INTO `wk_km_document` VALUES (86, 'Minutes of the meeting', 'Translation', 3, 0, 1, 25, 0, 88, NULL, '2020-08-22 16:11:27', 14773, '2020-08-22 16:11:27', NULL, NULL);
INSERT INTO `wk_km_document` VALUES (87, 'Technical Documentation', 'Translation', 3, 0, 1, 25, 0, 89, NULL, '2020-08-22 16:11:27', 14773, '2020-08-22 16:11:27', NULL, NULL);
INSERT INTO `wk_km_document` VALUES (88, 'Competitive Analysis', 'Translation', 3, 0, 1, 25, 0, 90, NULL, '2020-08-22 16:11:27', 14773, '2020-08-22 16:11:27', NULL, NULL);
INSERT INTO `wk_km_document` VALUES (89, 'Product planning', 'Text .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-alt: solid windowtext .5pt; padding: 0cm 5.4pt 0cm 5.4pt;\" valign=\"top\" width=\"170\">\n<p style=\"mso-margin-top-alt: auto; mso-margin-bottom-alt: auto; mso-pagination: widow-orphan;\">截止时间</p>\n</td>\n</tr>\n<tr style=\"mso-yfti-irow: 2; mso-yfti-lastrow: yes;\">\n<td style=\"width: 155.7pt; border: solid windowtext 1.0pt; border-top: none; mso-border-top-alt: solid windowtext .5pt; mso-border-alt: solid windowtext .5pt; padding: 0cm 5.4pt 0cm 5.4pt;\" valign=\"top\" width=\"208\">\n<p style=\"mso-margin-top-alt: auto; mso-margin-bottom-alt: auto; mso-pagination: widow-orphan;\">工作项<span lang=\"EN-US\">2</span></p>\n</td>\n<td style=\"width: 77.95pt; border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-alt: solid windowtext .5pt; padding: 0cm 5.4pt 0cm 5.4pt;\" valign=\"top\" width=\"104\">\n<p style=\"mso-margin-top-alt: auto; mso-margin-bottom-alt: auto; mso-pagination: widow-orphan;\"><span lang=\"EN-US\">&nbsp;</span></p>\n</td>\n<td style=\"width: 127.6pt; border-top: none; border-left: none; border-bottom: solid windowtext 1.0pt; border-right: solid windowtext 1.0pt; mso-border-top-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-alt: solid windowtext .5pt; padding: 0cm 5.4pt 0cm 5.4pt;\" valign=\"top\" width=\"170\">\n<p style=\"mso-margin-top-alt: auto; mso-margin-bottom-alt: auto; mso-pagination: widow-orphan;\"><span lang=\"EN-US\">&nbsp;</span></p>\n</td>\n</tr>\n</tbody>\n</table>\n<p class=\"MsoNormal\"><span lang=\"EN-US\">&nbsp;</span></p>', 3, 0, 1, 25, 0, 91, NULL, '2020-08-22 16:11:27', 14773, '2020-08-22 16:11:27', NULL, NULL);

-- ----------------------------
-- Table structure for wk_km_document_favor
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_document_favor`;
CREATE TABLE `wk_km_document_favor`  (
  `favor_id` int(0) NOT NULL AUTO_INCREMENT,
  `document_id` int(0) NULL DEFAULT NULL,
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`favor_id`) USING BTREE,
  UNIQUE INDEX `wk_km_document_favor_document_id_create_user_id_uindex`(`document_id`, `create_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Document Like Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_document_favor
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_document_label
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_document_label`;
CREATE TABLE `wk_km_document_label`  (
  `label_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`label_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Document Tag Sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_document_label
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_document_share
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_document_share`;
CREATE TABLE `wk_km_document_share`  (
  `share_id` int(0) NOT NULL AUTO_INCREMENT,
  `document_id` int(0) NULL DEFAULT NULL COMMENT 'Document id',
  `share_user_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Share internal member id',
  `share_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'External share link',
  `token` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Unique ID for externally viewed documents',
  `status` int(0) NULL DEFAULT NULL COMMENT '1 enable 0 disable sharing',
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `close_user_id` bigint(0) NULL DEFAULT NULL,
  `close_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`share_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Document sharing' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_document_share
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_folder
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_folder`;
CREATE TABLE `wk_km_folder`  (
  `folder_id` int(0) NOT NULL AUTO_INCREMENT,
  `library_id` int(0) NULL DEFAULT NULL COMMENT 'Knowledge base id',
  `parent_id` int(0) NULL DEFAULT 0 COMMENT 'Father id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `status` int(0) NULL DEFAULT 1 COMMENT '-1 delete 1 normal',
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `auth_id` int(0) NULL DEFAULT NULL,
  `delete_user_id` bigint(0) NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`folder_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Knowledge base folder' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_folder
-- ----------------------------

-- ----------------------------
-- Table structure for wk_km_knowledge_library
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_knowledge_library`;
CREATE TABLE `wk_km_knowledge_library`  (
  `library_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Knowledge base name',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Introduction',
  `is_open` int(0) NOT NULL COMMENT 'Is it public 1 public 2 private',
  `status` int(0) NULL DEFAULT 1 COMMENT '-1 delete 1 normal 2 template',
  `is_system_cover` int(0) NULL DEFAULT NULL COMMENT '0 no 1 yes',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Knowledge Base Cover',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Founder',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `delete_user_id` bigint(0) NULL DEFAULT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`library_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Knowledge base' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_knowledge_library
-- ----------------------------
INSERT INTO `wk_km_knowledge_library` VALUES (25, 'Product development', 'Provide complete product process documentation', 0, 1, 1, 'https://www.72crm.com/api/uploads/kw/1.png', 14773, '2020-08-22 16:11:27', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wk_km_knowledge_library_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_km_knowledge_library_user`;
CREATE TABLE `wk_km_knowledge_library_user`  (
  `r_id` int(0) NOT NULL AUTO_INCREMENT,
  `library_id` int(0) NOT NULL,
  `user_id` bigint(0) NOT NULL,
  `role` int(0) NULL DEFAULT NULL COMMENT '1 Creator 2 Administrators 3 Members',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Knowledge base member' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_km_knowledge_library_user
-- ----------------------------
INSERT INTO `wk_km_knowledge_library_user` VALUES (55, 25, 14773, 1);

-- ----------------------------
-- Table structure for wk_oa_announcement
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_announcement`;
CREATE TABLE `wk_oa_announcement`  (
  `announcement_id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Title',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Content',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Starting time',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT 'End Time',
  `dept_ids` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Notify the department',
  `owner_user_ids` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Alert others',
  `read_user_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Read user',
  PRIMARY KEY (`announcement_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Announcement Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_announcement
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_calendar_type
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_calendar_type`;
CREATE TABLE `wk_oa_calendar_type`  (
  `type_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Calendar type id',
  `type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Type name',
  `color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Color',
  `type` int(0) NULL DEFAULT NULL COMMENT '1 System type 2 Custom type',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT 1,
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 500 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Calendar type' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_calendar_type
-- ----------------------------
INSERT INTO `wk_oa_calendar_type` VALUES (492, 'Assigned tasks', '1', 1, '2020-01-13 09:44:05', 14773, NULL, 1);
INSERT INTO `wk_oa_calendar_type` VALUES (493, 'Leads to contact', '5', 1, '2020-01-13 09:44:05', 14773, NULL, 2);
INSERT INTO `wk_oa_calendar_type` VALUES (494, 'Customers to contact', '2', 1, '2020-01-13 09:44:05', 14773, NULL, 3);
INSERT INTO `wk_oa_calendar_type` VALUES (495, 'Opportunities to contact', '6', 1, '2020-01-13 09:44:05', 14773, NULL, 4);
INSERT INTO `wk_oa_calendar_type` VALUES (496, 'Estimated business opportunity', '7', 1, '2020-01-13 09:44:05', 14773, NULL, 5);
INSERT INTO `wk_oa_calendar_type` VALUES (497, 'Expiring contract', '3', 1, '2020-01-13 09:44:05', 14773, NULL, 6);
INSERT INTO `wk_oa_calendar_type` VALUES (498, 'Plan to pay back', '4', 1, '2020-01-13 09:44:05', 14773, NULL, 7);
INSERT INTO `wk_oa_calendar_type` VALUES (499, 'Meeting', '#53D397', 2, '2020-01-13 09:44:05', 14773, NULL, 1);

-- ----------------------------
-- Table structure for wk_oa_calendar_type_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_calendar_type_user`;
CREATE TABLE `wk_oa_calendar_type_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  `type_id` int(0) NOT NULL COMMENT 'Calendar type id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8738 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'User associated calendar type' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_calendar_type_user
-- ----------------------------
INSERT INTO `wk_oa_calendar_type_user` VALUES (8733, 14773, 487);
INSERT INTO `wk_oa_calendar_type_user` VALUES (8734, 14773, 488);
INSERT INTO `wk_oa_calendar_type_user` VALUES (8735, 14773, 489);
INSERT INTO `wk_oa_calendar_type_user` VALUES (8736, 14773, 490);
INSERT INTO `wk_oa_calendar_type_user` VALUES (8737, 14773, 491);

-- ----------------------------
-- Table structure for wk_oa_event
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_event`;
CREATE TABLE `wk_oa_event`  (
  `event_id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Title',
  `type_id` int(0) NOT NULL COMMENT 'Schedule type',
  `start_time` datetime(0) NOT NULL COMMENT 'Starting time',
  `end_time` datetime(0) NOT NULL COMMENT 'End Time',
  `owner_user_ids` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Participant',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `repetition_type` int(0) NULL DEFAULT 1 COMMENT 'Recurrence Type 1Never 2Daily 3Weekly 4Monthly 5Yearly',
  `repeat_rate` int(0) NULL DEFAULT NULL COMMENT 'Repeat frequency',
  `repeat_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '3:week/4:month',
  `end_type` int(0) NULL DEFAULT NULL COMMENT 'End Type 1Never 2Repeats 3EndDate',
  `end_type_config` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '2: times/3: time',
  `repeat_start_time` datetime(0) NOT NULL COMMENT 'Cycle start time',
  `repeat_end_time` datetime(0) NULL DEFAULT NULL COMMENT 'Loop end time',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`event_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Schedule' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_event
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_event_notice
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_event_notice`;
CREATE TABLE `wk_oa_event_notice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `event_id` int(0) NOT NULL COMMENT 'Schedule ID',
  `type` int(0) NOT NULL COMMENT '1 minute 2 hours 3 days',
  `value` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Schedule reminder setting table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_event_notice
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_event_relation
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_event_relation`;
CREATE TABLE `wk_oa_event_relation`  (
  `eventrelation_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Schedule related business table',
  `event_id` int(0) NOT NULL COMMENT 'Schedule ID',
  `customer_ids` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Customer IDs',
  `contacts_ids` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contact IDs',
  `business_ids` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Opportunity IDs',
  `contract_ids` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contract IDs',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  PRIMARY KEY (`eventrelation_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Schedule related business table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_event_relation
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_event_update_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_event_update_record`;
CREATE TABLE `wk_oa_event_update_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `event_id` int(0) NOT NULL,
  `time` bigint(0) NOT NULL COMMENT 'Title',
  `status` int(0) NULL DEFAULT NULL COMMENT '1 Delete this time 2 Modify this time 3 Modify this time and later',
  `new_event_id` int(0) NULL DEFAULT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Schedule' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_event_update_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_examine
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine`;
CREATE TABLE `wk_oa_examine`  (
  `examine_id` int(0) NOT NULL AUTO_INCREMENT,
  `category_id` int(0) NOT NULL DEFAULT 1 COMMENT 'Approval Type',
  `content` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Content',
  `remark` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `type_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Type of leave',
  `money` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Total amount of travel and reimbursement',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Starting time',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT 'End Time',
  `duration` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Duration',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Attachment batch id',
  `examine_record_id` int(0) NULL DEFAULT NULL COMMENT 'Audit record ID',
  `examine_status` int(0) NULL DEFAULT NULL COMMENT 'Review status 0 Not reviewed 1 Review passed 2 Review rejected 3 Reviewed 4 Withdrawn',
  PRIMARY KEY (`examine_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Approval Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_examine_category
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine_category`;
CREATE TABLE `wk_oa_examine_category`  (
  `category_id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Name',
  `remarks` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Describe',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Icon',
  `type` int(0) NULL DEFAULT 0 COMMENT '1 General Approval 2 Leave Approval 3 Business Trip Approval 4 Overtime Approval 5 Travel Reimbursement 6 Loan Application 0 Custom Approval',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `status` int(0) NULL DEFAULT 1 COMMENT '1 to enable, 0 to disable',
  `is_sys` int(0) NULL DEFAULT NULL COMMENT '1 is the system type and cannot be deleted',
  `examine_type` int(0) NULL DEFAULT NULL COMMENT '1 fixed 2 optional',
  `user_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Visible range (employees)',
  `dept_ids` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Visible range (department)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `is_deleted` int(0) NULL DEFAULT 0 COMMENT '1 deleted',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT 'Delete time',
  `delete_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Delete person id',
  PRIMARY KEY (`category_id`) USING BTREE,
  INDEX `create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 72985 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Approval Type Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine_category
-- ----------------------------
INSERT INTO `wk_oa_examine_category` VALUES (1072979, 'General approval', 'General approval', 'wk wk-l-record,#3ABCFB', 1, 3, 1, 1, 2, '', '', '2019-04-26 15:06:34', '2019-04-26 15:06:34', 0, NULL, NULL);
INSERT INTO `wk_oa_examine_category` VALUES (1072980, 'Leave approval', 'Leave approval', 'wk wk-leave,#00CAAB', 2, 3, 1, 1, 2, '', '', '2019-04-17 18:52:44', '2019-04-17 18:52:44', 0, NULL, NULL);
INSERT INTO `wk_oa_examine_category` VALUES (1072981, 'Travel approval', 'Travel approval', 'wk wk-trip,#3ABCFB', 3, 3, 1, 1, 2, '', '', '2019-04-17 18:52:50', '2019-04-17 18:52:50', 0, NULL, NULL);
INSERT INTO `wk_oa_examine_category` VALUES (1072982, 'Overtime approval', 'Overtime approval', 'wk wk-overtime,#FAAD14', 4, 3, 1, 1, 2, '', '', '2019-04-17 18:52:59', '2019-04-17 18:52:59', 0, NULL, NULL);
INSERT INTO `wk_oa_examine_category` VALUES (1072983, 'Travel reimbursement', 'Travel reimbursement', 'wk wk-reimbursement,#3ABCFB', 5, 3, 1, 1, 2, '', '', '2019-04-17 18:53:13', '2019-04-17 18:53:13', 0, NULL, NULL);
INSERT INTO `wk_oa_examine_category` VALUES (1072984, 'Loan application', 'Loan application', 'wk wk-go-out,#FF6033', 6, 3, 1, 1, 2, '', '', '2019-04-17 18:54:44', '2019-04-17 18:54:44', 0, NULL, NULL);

-- ----------------------------
-- Table structure for wk_oa_examine_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine_data`;
CREATE TABLE `wk_oa_examine_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL COMMENT 'Field id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime(0) NOT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Oa approval custom field value table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_examine_field
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine_field`;
CREATE TABLE `wk_oa_examine_field`  (
  `field_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Custom field English identification',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Field Name',
  `type` int(0) NOT NULL DEFAULT 1 COMMENT 'Field Type 1 Single Line Text 2 Multi Line Text 3 Single Choice 4 Date 5 Number 6 Decimal 7 Mobile Phone 8 File 9 Multiple Choice 10 Person 11 Attachment 12 Department 13 Date Time 14 Email 15 Customer 16 Business Opportunity 17 Contact 18 Map 19 Product Type 20 Contract 21 Repayment plan',
  `remark` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field Description',
  `input_tips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Input hint',
  `max_length` int(0) NULL DEFAULT NULL COMMENT 'The maximum length',
  `default_value` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Defaults',
  `is_unique` int(0) NULL DEFAULT 0 COMMENT 'Is it unique 1 yes 0 no',
  `is_null` int(0) NULL DEFAULT 0 COMMENT 'Required 1 Yes 0 No',
  `sorting` int(0) NULL DEFAULT 1 COMMENT 'Sort from smallest to largest',
  `options` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'If the type is an option, it cannot be empty here. Multiple options are separated by',
  `operating` int(0) NULL DEFAULT 255 COMMENT 'Is it possible to delete modifications',
  `is_hidden` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to hide 0 not to hide 1 to hide',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Last Modified',
  `examine_category_id` int(0) NULL DEFAULT NULL COMMENT 'Approval ID label of 10 is required',
  `field_type` int(0) NOT NULL DEFAULT 0 COMMENT 'Field source 0. Custom 1. Original fixed 2 Original field but the value exists in the extension table',
  `style_percent` int(0) NULL DEFAULT 50 COMMENT 'Style percentage %',
  `precisions` int(0) NULL DEFAULT NULL COMMENT 'Precision, maximum allowed decimal places',
  `form_position` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Form positioning Coordinate format: 1,1',
  `max_num_restrict` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Maximum value limit',
  `min_num_restrict` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'The minimum value of the limit',
  `form_assist_id` int(0) NULL DEFAULT NULL COMMENT 'Form auxiliary id, front-end generated',
  PRIMARY KEY (`field_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 572 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Custom field table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine_field
-- ----------------------------
INSERT INTO `wk_oa_examine_field` VALUES (548, 'content', 'Approved content', 1, NULL, '', NULL, '', 0, 1, 0, NULL, 3, 0, '2021-01-09 16:03:54', 1072979, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (549, 'remark', 'Remark', 2, NULL, '', 1000, '', 0, 0, 1, NULL, 3, 0, '2021-01-09 16:03:54', 1072979, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (550, 'type_id', 'Type of leave', 3, NULL, '', NULL, 'Annual leave', 0, 1, 0, 'Annual leave, personal leave, sick leave, maternity leave, sabbatical leave, marriage leave, bereavement leave, other', 3, 0, '2021-01-09 16:03:54', 1072980, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (551, 'content', 'Approved content', 1, NULL, '', NULL, '', 0, 1, 1, NULL, 3, 0, '2021-01-09 16:03:54', 1072980, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (552, 'start_time', 'Starting time', 13, NULL, '', NULL, '', 0, 1, 2, NULL, 3, 0, '2021-01-09 16:03:54', 1072980, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (553, 'end_time', 'End Time', 13, NULL, '', NULL, '', 0, 1, 3, NULL, 3, 0, '2021-01-09 16:03:54', 1072980, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (554, 'duration', 'Duration (days)', 6, NULL, '', NULL, '', 0, 1, 4, NULL, 3, 0, '2021-05-07 13:58:14', 1072980, 1, 50, 2, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (555, 'remark', 'Remark', 2, NULL, '', 1000, '', 0, 0, 5, NULL, 3, 0, '2021-01-09 16:03:54', 1072980, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (556, 'content', 'Business trip', 1, NULL, '', NULL, '', 0, 1, 0, NULL, 3, 0, '2021-01-09 16:03:54', 1072981, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (557, 'remark', 'Remark', 2, NULL, '', 1000, '', 0, 0, 1, NULL, 3, 0, '2021-01-09 16:03:54', 1072981, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (558, 'duration', 'Total business days', 6, NULL, '', NULL, '', 0, 1, 2, NULL, 3, 0, '2021-05-07 13:58:14', 1072981, 1, 50, 2, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (559, 'cause', 'Itinerary details', 22, NULL, '', NULL, '', 0, 1, 2, NULL, 3, 0, '2021-01-09 16:03:54', 1072981, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (560, 'content', 'Reason for overtime', 1, NULL, '', NULL, '', 0, 1, 0, NULL, 3, 0, '2021-01-09 16:03:54', 1072982, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (561, 'start_time', 'Starting time', 13, NULL, '', NULL, '', 0, 1, 1, NULL, 3, 0, '2021-01-09 16:03:54', 1072982, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (562, 'end_time', 'End Time', 13, NULL, '', NULL, '', 0, 1, 2, NULL, 3, 0, '2021-01-09 16:03:54', 1072982, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (563, 'duration', 'Total days of overtime', 6, NULL, '', NULL, '', 0, 1, 3, NULL, 3, 0, '2021-05-07 13:58:14', 1072982, 1, 50, 2, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (564, 'remark', 'Remark', 2, NULL, '', 1000, '', 0, 0, 4, NULL, 3, 0, '2021-01-09 16:03:54', 1072982, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (565, 'content', 'Reason for travel', 1, NULL, '', NULL, '', 0, 1, 0, NULL, 3, 0, '2021-01-09 16:03:54', 1072983, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (566, 'money', 'Total reimbursement amount', 6, NULL, '', 0, '', 0, 1, 1, NULL, 3, 0, '2021-05-07 13:58:14', 1072983, 1, 50, 2, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (567, 'remark', 'Remark', 2, NULL, '', 1000, '', 0, 0, 2, NULL, 3, 0, '2021-01-09 16:03:54', 1072983, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (568, 'cause', 'Charge Details', 23, NULL, '', 1000, '', 0, 0, 2, NULL, 3, 0, '2021-01-09 16:03:54', 1072983, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (569, 'content', 'Reason for borrowing', 1, NULL, '', NULL, '', 0, 1, 0, NULL, 3, 0, '2021-01-09 16:03:54', 1072984, 1, 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (570, 'money', 'Loan amount (₩)', 6, NULL, '', 0, '', 0, 1, 1, NULL, 3, 0, '2021-05-07 13:58:14', 1072984, 1, 50, 2, NULL, NULL, NULL, NULL);
INSERT INTO `wk_oa_examine_field` VALUES (571, 'remark', 'Remark', 2, NULL, '', 1000, '', 0, 0, 2, NULL, 3, 0, '2021-01-09 16:03:54', 1072984, 1, 50, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wk_oa_examine_field_extend
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine_field_extend`;
CREATE TABLE `wk_oa_examine_field_extend`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `parent_field_id` int(0) NOT NULL COMMENT 'Corresponding main field id',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Custom field English identification',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Field Name',
  `type` int(0) NOT NULL DEFAULT 1 COMMENT 'Field Type 1 Single Line Text 2 Multi Line Text 3 Single Choice 4 Date 5 Number 6 Decimal 7 Mobile Phone 8 File 9 Multiple Choice 10 Person 11 Attachment 12 Department 13 Date Time 14 Email 15 Customer 16 Business Opportunity 17 Contact 18 Map 19 Product Type 20 Contract 21 Repayment plan',
  `remark` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field Description',
  `input_tips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Input hint',
  `max_length` int(0) NULL DEFAULT NULL COMMENT 'The maximum length',
  `default_value` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Defaults',
  `is_unique` int(0) NULL DEFAULT 0 COMMENT 'Is it unique 1 yes 0 no',
  `is_null` int(0) NULL DEFAULT 0 COMMENT 'Required 1 Yes 0 No',
  `sorting` int(0) NULL DEFAULT 1 COMMENT 'Sort from smallest to largest',
  `options` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'If the type is an option, it cannot be empty here. Multiple options are separated by',
  `operating` int(0) NULL DEFAULT 255 COMMENT 'Is it possible to delete modifications',
  `is_hidden` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to hide 0 not to hide 1 to hide',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Last Modified',
  `field_type` int(0) NOT NULL DEFAULT 0 COMMENT 'Field source 0. Custom 1. Original fixed 2 Original field but the value exists in the extension table',
  `style_percent` int(0) NULL DEFAULT 50 COMMENT 'Style percentage %',
  `precisions` int(0) NULL DEFAULT NULL COMMENT 'Precision, maximum allowed decimal places',
  `form_position` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Form positioning Coordinate format: 1,1',
  `max_num_restrict` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Maximum value limit',
  `min_num_restrict` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'The minimum value of the limit',
  `form_assist_id` int(0) NULL DEFAULT NULL COMMENT 'Form auxiliary id, front-end generated',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5645 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Custom field table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine_field_extend
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_examine_log
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine_log`;
CREATE TABLE `wk_oa_examine_log`  (
  `log_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `record_id` int(0) NULL DEFAULT NULL COMMENT 'Approval record ID',
  `examine_step_id` bigint(0) NULL DEFAULT NULL COMMENT 'Audit Step ID',
  `examine_status` int(0) NULL DEFAULT NULL COMMENT 'Approval Status 0 Unapproved 1 Approved 2 Approved Rejected 4 Withdrawn Approval',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT 'Founder',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `examine_user` bigint(0) NULL DEFAULT NULL COMMENT 'Reviewer',
  `examine_time` datetime(0) NULL DEFAULT NULL COMMENT 'Review time',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Review Notes',
  `is_recheck` int(0) NULL DEFAULT 0 COMMENT 'Whether it is the log before the withdrawal 0 or null for the new data 1: the data before the withdrawal',
  `order_id` int(0) NULL DEFAULT NULL COMMENT 'Sort id',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Audit log table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine_log
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_examine_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine_record`;
CREATE TABLE `wk_oa_examine_record`  (
  `record_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Audit record ID',
  `examine_id` int(0) NULL DEFAULT NULL COMMENT 'Approval ID',
  `examine_step_id` bigint(0) NULL DEFAULT NULL COMMENT 'Approval step ID currently in progress',
  `examine_status` int(0) NULL DEFAULT NULL COMMENT 'Review status 0 Not reviewed 1 Review passed 2 Review rejected 3 Reviewed 4 Withdrawn',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT 'Founder',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Review Notes',
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Audit record sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_examine_relation
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine_relation`;
CREATE TABLE `wk_oa_examine_relation`  (
  `r_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Approval of related business tables',
  `examine_id` int(0) NULL DEFAULT NULL COMMENT 'Approval ID',
  `customer_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Customer IDs',
  `contacts_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contact IDs',
  `business_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Opportunity IDs',
  `contract_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contract IDs',
  `status` int(0) NULL DEFAULT 1 COMMENT 'Status 1 available',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Approval of related business tables' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine_relation
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_examine_sort
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine_sort`;
CREATE TABLE `wk_oa_examine_sort`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `category_id` int(0) NULL DEFAULT NULL COMMENT 'Approval type id',
  `sort` int(0) NULL DEFAULT NULL COMMENT 'Sort',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Userid',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Sort by approval type' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine_sort
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_examine_step
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine_step`;
CREATE TABLE `wk_oa_examine_step`  (
  `step_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `step_type` int(0) NULL DEFAULT NULL COMMENT 'Step type 1. Responsible person in charge, 2. Designated user (any one person), 3. Designated user (multi-person countersignment), 4. Supervisor of the upper-level approver',
  `category_id` int(0) NOT NULL COMMENT 'Approval ID',
  `check_user_id` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Approver IDs (separated by commas) ,1,2,',
  `step_num` int(0) NULL DEFAULT 1 COMMENT 'Sort',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  PRIMARY KEY (`step_id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Approval step table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine_step
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_examine_travel
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_examine_travel`;
CREATE TABLE `wk_oa_examine_travel`  (
  `travel_id` int(0) NOT NULL AUTO_INCREMENT,
  `examine_id` int(0) NOT NULL COMMENT 'Approval ID',
  `start_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Departure',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Departure time',
  `end_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Destination',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT 'Time of arrival',
  `traffic` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Transportation',
  `stay` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Accommodation fee',
  `diet` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Dining fee',
  `other` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Other fee',
  `money` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Amount',
  `vehicle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Transportation',
  `trip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'One-way round-trip (one-way, round-trip)',
  `duration` decimal(18, 2) NULL DEFAULT NULL COMMENT 'Duration',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Batch id',
  PRIMARY KEY (`travel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Business trip itinerary' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_examine_travel
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_log
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_log`;
CREATE TABLE `wk_oa_log`  (
  `log_id` int(0) NOT NULL AUTO_INCREMENT,
  `category_id` int(0) NOT NULL DEFAULT 1 COMMENT 'Journal type (1 daily, 2 weekly, 3 monthly)',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Log title',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Log content',
  `tomorrow` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Tomorrow\'s work content',
  `question` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Encounter problems',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Update time',
  `send_user_ids` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Alert others',
  `send_dept_ids` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Notify the department',
  `read_user_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Readers',
  `batch_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'File batch id',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Work log sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_log
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_log_bulletin
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_log_bulletin`;
CREATE TABLE `wk_oa_log_bulletin`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `log_id` int(0) NULL DEFAULT NULL COMMENT 'Log id',
  `type` int(0) NULL DEFAULT NULL COMMENT 'Association type 1 Customer 2 Opportunity 3 Contract 4 Payment collection 5 Follow-up record',
  `type_id` int(0) NULL DEFAULT NULL COMMENT 'Type ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Work log and business ID association table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_log_bulletin
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_log_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_log_record`;
CREATE TABLE `wk_oa_log_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `log_id` int(0) NOT NULL,
  `customer_num` int(0) NULL DEFAULT 0 COMMENT 'Number of clients',
  `business_num` int(0) NULL DEFAULT 0 COMMENT 'Number of business opportunities',
  `contract_num` int(0) NULL DEFAULT 0 COMMENT 'Number of contracts',
  `receivables_money` decimal(10, 2) NULL DEFAULT 0.00 COMMENT 'Repayment amount',
  `activity_num` int(0) NULL DEFAULT 0 COMMENT 'Follow up record',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Log-Associated Sales Briefing Report' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_log_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_log_relation
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_log_relation`;
CREATE TABLE `wk_oa_log_relation`  (
  `r_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Log related business table',
  `log_id` int(0) NULL DEFAULT NULL COMMENT 'Log id',
  `customer_ids` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Customer IDs',
  `contacts_ids` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contact IDs',
  `business_ids` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Opportunity IDs',
  `contract_ids` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contract IDs',
  `status` int(0) NULL DEFAULT NULL COMMENT 'Status 1 available',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Log related business table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_log_relation
-- ----------------------------

-- ----------------------------
-- Table structure for wk_oa_log_rule
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_log_rule`;
CREATE TABLE `wk_oa_log_rule`  (
  `rule_id` int(0) NOT NULL AUTO_INCREMENT,
  `status` int(0) NOT NULL DEFAULT 1 COMMENT 'Status 0 disabled 1 enabled',
  `member_user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'The employee id that needs to be submitted, divided by ","',
  `type` int(0) NULL DEFAULT NULL COMMENT 'Log Type 1 Daily 2 Weekly 3 Monthly',
  `effective_day` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'The days that need to be counted every week 1-6 are Monday to Saturday 7 is Sunday',
  `start_day` int(0) NULL DEFAULT NULL COMMENT 'Start date',
  `start_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Starting time',
  `end_day` int(0) NULL DEFAULT NULL COMMENT 'End date',
  `end_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'End Time',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Founder',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  PRIMARY KEY (`rule_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 268 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Oa log rule table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_log_rule
-- ----------------------------
INSERT INTO `wk_oa_log_rule` VALUES (265, 1, NULL, 1, '1,2,3,4,5', NULL, '00:00', NULL, '23:00', 0, '2020-08-22 11:40:42');
INSERT INTO `wk_oa_log_rule` VALUES (266, 1, NULL, 2, NULL, 1, NULL, 7, NULL, 0, '2020-08-22 11:40:42');
INSERT INTO `wk_oa_log_rule` VALUES (267, 1, NULL, 3, NULL, 1, NULL, 31, NULL, 0, '2020-08-22 11:40:42');

-- ----------------------------
-- Table structure for wk_oa_log_user_favour
-- ----------------------------
DROP TABLE IF EXISTS `wk_oa_log_user_favour`;
CREATE TABLE `wk_oa_log_user_favour`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  `log_id` int(0) NOT NULL COMMENT 'Log id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'User like log relationship table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_oa_log_user_favour
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work
-- ----------------------------
DROP TABLE IF EXISTS `wk_work`;
CREATE TABLE `wk_work`  (
  `work_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Project ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Project name',
  `status` int(0) NULL DEFAULT 1 COMMENT 'Status 1 Enable 3 Archive 2 Delete',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `description` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT 'Describe',
  `color` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Color',
  `is_open` int(0) NOT NULL DEFAULT 1 COMMENT 'Is it visible to everyone 1 Yes 0 No',
  `owner_role` int(0) NULL DEFAULT NULL COMMENT 'Public project member role id',
  `archive_time` datetime(0) NULL DEFAULT NULL COMMENT 'Archive time',
  `delete_time` datetime(0) NULL DEFAULT NULL COMMENT 'Delete time',
  `is_system_cover` int(0) NULL DEFAULT NULL COMMENT 'Whether the system comes with a cover 0 is not 1 is',
  `cover_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Project cover path Only required for the system\'s own cover',
  `batch_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `owner_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Project member',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  PRIMARY KEY (`work_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Project table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work_collect
-- ----------------------------
DROP TABLE IF EXISTS `wk_work_collect`;
CREATE TABLE `wk_work_collect`  (
  `collect_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Item collection id',
  `work_id` int(0) NOT NULL COMMENT 'Project id',
  `user_id` bigint(0) NOT NULL COMMENT 'Userid',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`collect_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Project Favorites' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work_collect
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work_order
-- ----------------------------
DROP TABLE IF EXISTS `wk_work_order`;
CREATE TABLE `wk_work_order`  (
  `order_id` int(0) NOT NULL AUTO_INCREMENT,
  `work_id` int(0) NOT NULL,
  `user_id` bigint(0) NOT NULL,
  `order_num` int(0) NOT NULL DEFAULT 999,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Project Sort Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work_order
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work_task
-- ----------------------------
DROP TABLE IF EXISTS `wk_work_task`;
CREATE TABLE `wk_work_task`  (
  `task_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Task list',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Mission name',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `main_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Responsible person ID',
  `owner_user_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Team member ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'New time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  `status` int(0) NULL DEFAULT 1 COMMENT 'Completion Status 1 In Progress 2 Deferred 3 Archived 5 Closed',
  `class_id` int(0) NULL DEFAULT -1 COMMENT 'Category id',
  `label_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Label , number splicing',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT 'Describe',
  `pid` int(0) NULL DEFAULT 0 COMMENT 'Superior ID',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Starting time',
  `stop_time` datetime(0) NULL DEFAULT NULL COMMENT 'End Time',
  `priority` int(0) NULL DEFAULT 0 COMMENT 'Priority from large to small 3 high 2 medium 1 low 0 none',
  `work_id` int(0) NULL DEFAULT 0 COMMENT 'Project ID',
  `is_top` int(0) NULL DEFAULT 0 COMMENT 'Workbench Display 0 Inbox 1 to do today, 2 to do next, 3 to do later',
  `is_open` int(0) NULL DEFAULT 1 COMMENT 'Is it public',
  `order_num` int(0) NULL DEFAULT 999 COMMENT 'Sort ID',
  `top_order_num` int(0) NULL DEFAULT 999 COMMENT 'My task sort id',
  `archive_time` datetime(0) NULL DEFAULT NULL COMMENT 'Archive time',
  `ishidden` int(0) NULL DEFAULT 0 COMMENT 'Whether to delete 0 Not deleted 1 Deleted',
  `hidden_time` datetime(0) NULL DEFAULT NULL COMMENT 'Delete time',
  `batch_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Batch',
  `is_archive` int(0) NULL DEFAULT 0 COMMENT '1 Archive',
  PRIMARY KEY (`task_id`) USING BTREE,
  INDEX `pid`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Task list' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work_task
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work_task_class
-- ----------------------------
DROP TABLE IF EXISTS `wk_work_task_class`;
CREATE TABLE `wk_work_task_class`  (
  `class_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Task classification table',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Category name',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `status` int(0) NULL DEFAULT 0 COMMENT 'State 1 normal',
  `work_id` int(0) NULL DEFAULT NULL COMMENT 'Project ID',
  `order_num` int(0) NULL DEFAULT NULL COMMENT 'Sort',
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Task classification table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work_task_class
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work_task_comment
-- ----------------------------
DROP TABLE IF EXISTS `wk_work_task_comment`;
CREATE TABLE `wk_work_task_comment`  (
  `comment_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Comment form',
  `user_id` bigint(0) NOT NULL COMMENT 'Reviewer ID',
  `content` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Content (answer)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'New time',
  `main_id` int(0) NULL DEFAULT 0 COMMENT 'The id of the main comment',
  `pid` bigint(0) NULL DEFAULT 0 COMMENT 'Reply object ID',
  `status` int(0) NULL DEFAULT 1 COMMENT 'State',
  `type_id` int(0) NULL DEFAULT 0 COMMENT 'Comment project task ID or comment other module ID',
  `favour` int(0) NULL DEFAULT 0 COMMENT 'Awesome',
  `type` int(0) NULL DEFAULT 0 COMMENT 'Comment Category 1: Task Comments 2: Log Comments',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Task Comment Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work_task_comment
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work_task_label
-- ----------------------------
DROP TABLE IF EXISTS `wk_work_task_label`;
CREATE TABLE `wk_work_task_label`  (
  `label_id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Label name',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT 'Creator ID',
  `status` int(0) NULL DEFAULT 0 COMMENT 'State',
  `color` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT 'Color',
  PRIMARY KEY (`label_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Task Label Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work_task_label
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work_task_label_order
-- ----------------------------
DROP TABLE IF EXISTS `wk_work_task_label_order`;
CREATE TABLE `wk_work_task_label_order`  (
  `order_id` int(0) NOT NULL AUTO_INCREMENT,
  `label_id` int(0) NOT NULL,
  `user_id` bigint(0) NOT NULL,
  `order_num` int(0) NOT NULL DEFAULT 999,
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Item Label Sort Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work_task_label_order
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work_task_log
-- ----------------------------
DROP TABLE IF EXISTS `wk_work_task_log`;
CREATE TABLE `wk_work_task_log`  (
  `log_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Project log sheet',
  `user_id` bigint(0) NOT NULL COMMENT 'Operator ID',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT 'Content',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `status` int(0) NULL DEFAULT 0 COMMENT 'State 4 delete',
  `task_id` int(0) NULL DEFAULT 0 COMMENT 'Task id',
  `work_id` int(0) NULL DEFAULT 0 COMMENT 'Project ID',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Task log table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work_task_log
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work_task_relation
-- ----------------------------
DROP TABLE IF EXISTS `wk_work_task_relation`;
CREATE TABLE `wk_work_task_relation`  (
  `r_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Task-related business table',
  `task_id` int(0) NULL DEFAULT NULL COMMENT 'Task id',
  `customer_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Customer IDs',
  `contacts_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contact IDs',
  `business_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Opportunity IDs',
  `contract_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Contract IDs',
  `status` int(0) NULL DEFAULT NULL COMMENT 'Status 1 available',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  PRIMARY KEY (`r_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Task-related business table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work_task_relation
-- ----------------------------

-- ----------------------------
-- Table structure for wk_work_user
-- ----------------------------
DROP TABLE IF EXISTS `wk_work_user`;
CREATE TABLE `wk_work_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `work_id` int(0) NOT NULL COMMENT 'Project ID',
  `user_id` bigint(0) NOT NULL COMMENT 'Member ID',
  `role_id` int(0) NOT NULL COMMENT 'Role id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Project member table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_work_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
