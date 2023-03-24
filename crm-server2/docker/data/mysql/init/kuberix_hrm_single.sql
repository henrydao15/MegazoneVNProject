create database kuberix_hrm_single character set utf8mb4 collate utf8mb4_general_ci;
use kuberix_hrm_single;
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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_appraisal
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_appraisal`;
CREATE TABLE `wk_hrm_achievement_appraisal`  (
  `appraisal_id` int(0) NOT NULL AUTO_INCREMENT,
  `appraisal_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Assessment name',
  `cycle_type` int(0) NULL DEFAULT NULL COMMENT 'January 2 quarters 3 years 4 half years',
  `start_time` date NULL DEFAULT NULL COMMENT 'Exam start time',
  `end_time` date NULL DEFAULT NULL COMMENT 'Exam end time',
  `table_id` int(0) NULL DEFAULT NULL COMMENT 'Assessment form template id',
  `written_by` int(0) NULL DEFAULT 1 COMMENT 'The person who fills in the assessment target 1 person',
  `result_confirmors` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'The person who confirms the assessment results\n',
  `full_score` decimal(7, 2) NULL DEFAULT NULL COMMENT 'Total score',
  `is_force` int(0) NULL DEFAULT NULL COMMENT 'Whether to enable forced distribution 1 Yes 0 No',
  `employee_ids` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Assess employees',
  `dept_ids` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Assessment department',
  `appraisal_steps` int(0) NULL DEFAULT -1 COMMENT 'Check the progress of the steps',
  `activate_steps` int(0) NULL DEFAULT -1 COMMENT 'Check the progress of the steps',
  `status` int(0) NULL DEFAULT 0 COMMENT 'Check the progress of the steps',
  `is_stop` int(0) NULL DEFAULT 0 COMMENT 'Terminate 0 No 1 Yes',
  `stop_time` datetime(0) NULL DEFAULT NULL COMMENT 'Stop the time',
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`appraisal_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Performance appraisal' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_appraisal
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_appraisal_evaluators
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_appraisal_evaluators`;
CREATE TABLE `wk_hrm_achievement_appraisal_evaluators`  (
  `evaluators_id` int(0) NOT NULL AUTO_INCREMENT,
  `appraisal_id` int(0) NOT NULL COMMENT 'Assessment id',
  `type` int(0) NOT NULL COMMENT '1 The employee himself 2 The direct superior 3 The person in charge of the department 4 The person in charge of the superior department 5 The designated target confirmer',
  `employee_id` int(0) NULL DEFAULT NULL COMMENT 'Specify the confirmer id',
  `weight` decimal(5, 2) NOT NULL COMMENT 'Weights',
  `sort` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`evaluators_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Assessment result assessor' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_appraisal_evaluators
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_appraisal_score_level
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_appraisal_score_level`;
CREATE TABLE `wk_hrm_achievement_appraisal_score_level`  (
  `level_id` int(0) NOT NULL AUTO_INCREMENT,
  `appraisal_id` int(0) NOT NULL COMMENT 'Assessment id',
  `level_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Class name',
  `min_score` decimal(7, 2) NULL DEFAULT NULL COMMENT 'Minimum score',
  `max_score` decimal(7, 2) NOT NULL COMMENT 'Max score',
  `min_num` int(0) NOT NULL COMMENT 'Minimum number of people',
  `max_num` int(0) NOT NULL COMMENT 'Maximum number of people',
  `sort` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`level_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Assessment Rule Level' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_appraisal_score_level
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_appraisal_target_confirmors
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_appraisal_target_confirmors`;
CREATE TABLE `wk_hrm_achievement_appraisal_target_confirmors`  (
  `target_confirmors_id` int(0) NOT NULL AUTO_INCREMENT,
  `appraisal_id` int(0) NULL DEFAULT NULL COMMENT 'Assessment id',
  `type` int(0) NULL DEFAULT NULL COMMENT '1 The employee himself 2 The direct superior 3 The person in charge of the department 4 The person in charge of the superior department 5 The designated target confirmer',
  `employee_id` int(0) NULL DEFAULT NULL COMMENT 'Specify the confirmer id',
  `sort` int(0) NULL DEFAULT NULL COMMENT 'Step number from small to large',
  PRIMARY KEY (`target_confirmors_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Check target confirmer' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_appraisal_target_confirmors
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_employee_appraisal
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_employee_appraisal`;
CREATE TABLE `wk_hrm_achievement_employee_appraisal`  (
  `employee_appraisal_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NOT NULL COMMENT 'Employee id',
  `appraisal_id` int(0) NULL DEFAULT NULL COMMENT 'Performance id',
  `status` int(0) NULL DEFAULT NULL COMMENT 'Assessment status 1 To be filled in 2 To be confirmed 3 To be assessed 4 To be confirmed by results 5 To terminate the performance 6 To complete the assessment',
  `score` double(10, 2) NULL DEFAULT NULL COMMENT 'Score',
  `level_id` int(0) NULL DEFAULT NULL COMMENT 'Assessment results',
  `read_status` int(0) NULL DEFAULT 0 COMMENT 'Result read status 0 unread 1 read',
  `follow_up_employee_id` int(0) NULL DEFAULT NULL COMMENT 'Follow up employee id',
  `follow_sort` int(0) NULL DEFAULT NULL COMMENT 'Follow up employee sorting',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `is_draft` int(0) NULL DEFAULT 0 COMMENT 'Is it a draft 0 no 1 yes',
  PRIMARY KEY (`employee_appraisal_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee performance appraisal' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_employee_appraisal
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_employee_evaluato
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_employee_evaluato`;
CREATE TABLE `wk_hrm_achievement_employee_evaluato`  (
  `employee_evaluato_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_appraisal_id` int(0) NULL DEFAULT NULL COMMENT 'Employee assessment id',
  `appraisal_id` int(0) NULL DEFAULT NULL COMMENT 'Performance id',
  `employee_id` int(0) NOT NULL COMMENT 'Confirmor',
  `weight` decimal(5, 2) NULL DEFAULT NULL COMMENT 'Weights',
  `score` decimal(7, 2) NULL DEFAULT NULL COMMENT 'Score',
  `level_id` int(0) NULL DEFAULT NULL COMMENT 'Assessment level',
  `evaluate` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Comments',
  `reject_reason` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Reason for rejection',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT 0 COMMENT '0 to be rated 1 to be rated',
  PRIMARY KEY (`employee_evaluato_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee Performance Results Evaluation Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_employee_evaluato
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_employee_evaluato_seg
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_employee_evaluato_seg`;
CREATE TABLE `wk_hrm_achievement_employee_evaluato_seg`  (
  `employee_evaluato_seg_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_appraisal_id` int(0) NULL DEFAULT NULL COMMENT 'Employee assessment id',
  `employee_evaluato_id` int(0) NULL DEFAULT NULL COMMENT 'Result rating id',
  `seg_id` int(0) NULL DEFAULT NULL COMMENT 'Assessment item id',
  `employee_id` int(0) NOT NULL COMMENT 'Rater',
  `score` decimal(7, 2) NULL DEFAULT NULL COMMENT 'Score',
  `evaluate` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Comments',
  `status` int(0) NULL DEFAULT 1 COMMENT '0 to be rated 1 to be rated',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`employee_evaluato_seg_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee Performance Evaluation Item Evaluation Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_employee_evaluato_seg
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_employee_result_confirmors
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_employee_result_confirmors`;
CREATE TABLE `wk_hrm_achievement_employee_result_confirmors`  (
  `confirmors_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NULL DEFAULT NULL,
  `appraisal_id` int(0) NULL DEFAULT NULL COMMENT 'Performance id',
  `status` int(0) NULL DEFAULT 0 COMMENT '0 not confirmed 1 confirmed',
  `sort` int(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  PRIMARY KEY (`confirmors_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Performance Results Confirmation Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_employee_result_confirmors
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_employee_seg
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_employee_seg`;
CREATE TABLE `wk_hrm_achievement_employee_seg`  (
  `seg_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_appraisal_id` int(0) NULL DEFAULT NULL,
  `temp_seg_id` int(0) NULL DEFAULT 0 COMMENT 'Template assessment item id',
  `employee_id` int(0) NULL DEFAULT NULL,
  `seg_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Exam item name',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Value',
  `is_fixed` int(0) NULL DEFAULT NULL COMMENT 'Fixed 1 yes 0 no',
  `weight` decimal(5, 2) NULL DEFAULT NULL COMMENT 'Weight -1 Employee write weight ratio 0~100',
  `schedule` int(0) NULL DEFAULT 0 COMMENT 'Target progress',
  `explain_desc` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Completion statement',
  `sort` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`seg_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee performance appraisal items' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_employee_seg
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_employee_seg_item
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_employee_seg_item`;
CREATE TABLE `wk_hrm_achievement_employee_seg_item`  (
  `item_id` int(0) NOT NULL AUTO_INCREMENT,
  `seg_id` int(0) NULL DEFAULT NULL,
  `temp_item_id` int(0) NULL DEFAULT 0 COMMENT 'Template assessment item id',
  `item_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Option name',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Value',
  `sort` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee Appraisal Item Options' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_employee_seg_item
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_employee_target_confirm
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_employee_target_confirm`;
CREATE TABLE `wk_hrm_achievement_employee_target_confirm`  (
  `employee_confirm_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_appraisal_id` int(0) NULL DEFAULT NULL COMMENT 'Employee assessment id',
  `appraisal_id` int(0) NULL DEFAULT NULL COMMENT 'Performance id',
  `employee_id` int(0) NOT NULL COMMENT 'Confirmor',
  `reject_reason` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Reason for rejection',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT 0 COMMENT '0 pending confirmation 1 confirmed 2 rejected',
  PRIMARY KEY (`employee_confirm_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee assessment target confirmation form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_employee_target_confirm
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_achievement_seg
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_seg`;
CREATE TABLE `wk_hrm_achievement_seg`  (
  `seg_id` int(0) NOT NULL AUTO_INCREMENT,
  `table_id` int(0) NULL DEFAULT NULL,
  `seg_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Exam item name',
  `is_fixed` int(0) NULL DEFAULT NULL COMMENT 'Fixed 1 yes 0 no',
  `weight` decimal(5, 2) NULL DEFAULT NULL COMMENT 'Weight -1 Employee write weight ratio 0~100',
  `sort` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`seg_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Performance Review Item Template' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_seg
-- ----------------------------
INSERT INTO `wk_hrm_achievement_seg` VALUES (33, 48, 'Key Performance (KP)', 0, NULL, 1);
INSERT INTO `wk_hrm_achievement_seg` VALUES (34, 49, 'Goal (O)', 0, NULL, 1);

-- ----------------------------
-- Table structure for wk_hrm_achievement_seg_item
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_seg_item`;
CREATE TABLE `wk_hrm_achievement_seg_item`  (
  `item_id` int(0) NOT NULL AUTO_INCREMENT,
  `seg_id` int(0) NULL DEFAULT NULL,
  `item_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Option name',
  `sort` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Check item options' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_seg_item
-- ----------------------------
INSERT INTO `wk_hrm_achievement_seg_item` VALUES (97, 33, 'KPI 1', 1);
INSERT INTO `wk_hrm_achievement_seg_item` VALUES (98, 33, 'KPI indicator 2', 2);
INSERT INTO `wk_hrm_achievement_seg_item` VALUES (99, 33, 'KPI Indicator 3', 3);
INSERT INTO `wk_hrm_achievement_seg_item` VALUES (100, 34, 'Key Results (kr)', 1);
INSERT INTO `wk_hrm_achievement_seg_item` VALUES (101, 34, 'Key Results (kr)', 2);
INSERT INTO `wk_hrm_achievement_seg_item` VALUES (102, 34, 'Key Results (kr)', 3);

-- ----------------------------
-- Table structure for wk_hrm_achievement_table
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_achievement_table`;
CREATE TABLE `wk_hrm_achievement_table`  (
  `table_id` int(0) NOT NULL AUTO_INCREMENT,
  `table_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Assessment name',
  `type` int(0) NULL DEFAULT NULL COMMENT '1 OKR template 2 KPI template',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Description of the assessment form',
  `is_emp_weight` int(0) NULL DEFAULT 0 COMMENT 'Whether the employee fills in the weight 0 No 1 Yes',
  `status` int(0) NULL DEFAULT 1 COMMENT '1 use 0 to delete',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Performance Review Form Template' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_achievement_table
-- ----------------------------
INSERT INTO `wk_hrm_achievement_table` VALUES (48, 'KPI template', 2, '1. Result-oriented principle: focus on performance target assessment, employees should always pay attention to job goals, fully understand team goals, and personal goals should support team goals. \n2. Key performance (KP) is decomposed from the job responsibilities, key tasks, team goals, etc. \n3. KPI is a key performance indicator for measuring the key performance (KP), which must be clearly targeted, quantifiable and easy to calculate. \n4. Fixed item assessment means that the company can set fixed assessment indicators according to the actual situation, which is applicable to all personnel within the assessment scope, and employees cannot be edited. For example: behavioral attitude assessment, ability and quality assessment, etc.', 1, 1, '2020-06-22 10:10:09', 14773);
INSERT INTO `wk_hrm_achievement_table` VALUES (49, 'OKR template', 1, '1. The main goal of OKRs is to define the "goals" of the company and the team and to define the measurable "key results" achieved by each goal. \n2. Individuals must first fully understand the overall goals of the team, and the setting of personal goals must support the team goals. \n3. In line with the "challenge-oriented" principle, the goal must be challenging, beyond the current status quo, measurable, and decomposable; the key results are the specific matters that support the implementation of the goal, and they must also be measurable. \n4. There are generally no more than 5 goals. Each goal is generally broken down into 1-3 key results, and the total weight is 100%.', 1, 1, '2020-06-23 10:43:46', 14773);

-- ----------------------------
-- Table structure for wk_hrm_action_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_action_record`;
CREATE TABLE `wk_hrm_action_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `ip_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Ip address',
  `type` int(0) NULL DEFAULT NULL COMMENT 'Operation Type 1 Employee 2 Recruitment Management 3 Candidate 4 Performance Management',
  `type_id` int(0) NULL DEFAULT NULL COMMENT 'Operation object id',
  `behavior` int(0) NULL DEFAULT NULL COMMENT 'Operational Behavior 1 New 2 Edit 3 Delete 4 Regular 5 Transfer 6 Promotion 7 Demotion 8 Full-time Employee 9 Resignation 10 Insurance Plan',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Content',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Operator ID',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `types`(`type`, `type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Hrm employee operation record sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_action_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_attendance_clock
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_attendance_clock`;
CREATE TABLE `wk_hrm_attendance_clock`  (
  `clock_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Punch record id',
  `clock_employee_id` int(0) NULL DEFAULT NULL,
  `clock_time` datetime(0) NOT NULL COMMENT 'Punch time',
  `clock_type` int(0) NOT NULL COMMENT 'Punch type 1 Punch at work 2 Punch at get off work',
  `attendance_time` datetime(0) NOT NULL COMMENT 'Work date',
  `type` int(0) NOT NULL DEFAULT 1 COMMENT 'Punch-in type 1. Mobile phone punch-in 2. Manual entry',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Attendance address',
  `lng` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Precision',
  `lat` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Dimension',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Remark',
  PRIMARY KEY (`clock_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Punch record sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_attendance_clock
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_config
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_config`;
CREATE TABLE `wk_hrm_config`  (
  `config_id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NULL DEFAULT NULL COMMENT 'Configuration Type 1 Elimination Reason 2 Salary Initialization Configuration 1 3 Salary Initialization Configuration 2 4 Social Security Initialization Configuration 1 5 Social Security Initialization Configuration 2',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Value',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 154 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Human resource allocation table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_config
-- ----------------------------
INSERT INTO `wk_hrm_config` VALUES (145, 2, '0', '2020-06-09 14:38:43');
INSERT INTO `wk_hrm_config` VALUES (146, 3, '0', '2020-06-09 14:38:43');
INSERT INTO `wk_hrm_config` VALUES (147, 4, '0', '2020-06-09 14:38:43');
INSERT INTO `wk_hrm_config` VALUES (148, 5, '0', '2020-06-09 14:38:43');
INSERT INTO `wk_hrm_config` VALUES (149, 1, 'Poor communication skills', '2020-06-23 10:47:47');
INSERT INTO `wk_hrm_config` VALUES (150, 1, 'Candidates give up', '2020-06-23 10:47:47');
INSERT INTO `wk_hrm_config` VALUES (151, 1, 'Salary requirements are too high', '2020-06-23 10:47:47');
INSERT INTO `wk_hrm_config` VALUES (152, 1, 'Poor stability', '2020-06-23 10:47:47');
INSERT INTO `wk_hrm_config` VALUES (153, 1, 'Little relevant experience', '2020-06-23 10:47:47');

-- ----------------------------
-- Table structure for wk_hrm_dept
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_dept`;
CREATE TABLE `wk_hrm_dept`  (
  `dept_id` int(0) NOT NULL AUTO_INCREMENT,
  `pid` int(0) NULL DEFAULT 0 COMMENT 'Parent ID top-level department is 0',
  `dept_type` int(0) NULL DEFAULT NULL COMMENT '1 company 2 departments',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Department name',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Department code',
  `main_employee_id` int(0) NULL DEFAULT NULL COMMENT 'Department Head ID',
  `leader_employee_id` int(0) NULL DEFAULT NULL COMMENT 'Leaders in charge',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Department table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_dept
-- ----------------------------
INSERT INTO `wk_hrm_dept` VALUES (18, 0, 1, 'Company-wide', '1', 2, 2, NULL, 14773);

-- ----------------------------
-- Table structure for wk_hrm_employee
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee`;
CREATE TABLE `wk_hrm_employee`  (
  `employee_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Employee id',
  `employee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Employee\'s name',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Cell phone',
  `country` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Country / region',
  `nation` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Nationality',
  `id_type` int(0) NULL DEFAULT NULL COMMENT 'Type of document 1 ID card 2 Passport 3 Others',
  `id_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ID number',
  `sex` int(0) NULL DEFAULT NULL COMMENT 'Gender 1 Male 2 Female',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Mail',
  `native_place` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Hometown',
  `date_of_birth` datetime(0) NULL DEFAULT NULL COMMENT 'Date of birth',
  `birthday_type` int(0) NULL DEFAULT 1 COMMENT 'Birthday type 1 Gregorian calendar 2 Lunar calendar',
  `birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Birthday Example: 0323',
  `age` int(0) NULL DEFAULT NULL COMMENT 'Age',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Residence address',
  `highest_education` int(0) NULL DEFAULT NULL COMMENT 'Highest education',
  `entry_time` datetime(0) NULL DEFAULT NULL COMMENT 'Entry Time',
  `probation` int(0) NULL DEFAULT NULL COMMENT 'Trial period 0 No trial period',
  `become_time` datetime(0) NULL DEFAULT NULL COMMENT 'Correction date',
  `job_number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `dept_id` int(0) NULL DEFAULT NULL COMMENT 'Department ID',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT 'Direct superior ID',
  `post` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Position',
  `post_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Job rank',
  `work_address` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Work place',
  `work_detail_address` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Working address',
  `work_city` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Work city',
  `channel_id` int(0) NULL DEFAULT NULL COMMENT 'Recruitment channels',
  `employment_forms` int(0) NULL DEFAULT NULL COMMENT 'Employment Form 1 Formal 2 Informal',
  `status` int(0) NULL DEFAULT NULL COMMENT 'Employee status 1 Formal 2 Trial 3 Internship 4 Part-time 5 Labor 6 Consultant 7 Re-employment 8 Outsourcing',
  `company_age_start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Company start date',
  `company_age` int(0) NULL DEFAULT 0 COMMENT 'Company days',
  `entry_status` int(0) NULL DEFAULT NULL COMMENT 'Entry status 1 Incumbent 2 To be hired 3 To leave 4 To leave',
  `candidate_id` int(0) NULL DEFAULT NULL COMMENT 'Candidate id',
  `is_del` int(0) NULL DEFAULT 0 COMMENT '0 not deleted 1 deleted',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Update time',
  PRIMARY KEY (`employee_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_abnormal_change_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_abnormal_change_record`;
CREATE TABLE `wk_hrm_employee_abnormal_change_record`  (
  `change_record_id` int(0) NOT NULL AUTO_INCREMENT,
  `type` int(0) NULL DEFAULT NULL COMMENT 'Type of change 1 New hire 2 Resignation 3 Regularization 4 Transfer',
  `employee_id` int(0) NULL DEFAULT NULL COMMENT 'Transfer employee id',
  `change_time` datetime(0) NULL DEFAULT NULL COMMENT 'Change time',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  PRIMARY KEY (`change_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee exception record table (required for salary list statistics)' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_abnormal_change_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_candidate
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_candidate`;
CREATE TABLE `wk_hrm_employee_candidate`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `employee_id` int(0) NULL DEFAULT NULL COMMENT 'Employee id',
  `candidate_id` int(0) NULL DEFAULT NULL COMMENT 'Candidate id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee Candidate Association Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_candidate
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_certificate
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_certificate`;
CREATE TABLE `wk_hrm_employee_certificate`  (
  `certificate_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NULL DEFAULT NULL COMMENT 'Employee id',
  `certificate_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Certificate name',
  `certificate_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Certificate level',
  `certificate_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Certificate No',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Effective from date',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT 'Effective end date',
  `issuing_authority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Issuing agency',
  `issuing_time` datetime(0) NULL DEFAULT NULL COMMENT 'Date of issue',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Remark',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`certificate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Employee certificate' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_certificate
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_change_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_change_record`;
CREATE TABLE `wk_hrm_employee_change_record`  (
  `record_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NULL DEFAULT NULL COMMENT 'Employee id',
  `change_type` int(0) NULL DEFAULT NULL COMMENT 'Type of change 4 Regularization 5 Transfer 6 Promotion 7 Demotion 8 Full-time employee',
  `change_reason` int(0) NULL DEFAULT NULL COMMENT 'Reasons for change 1 Organizational structure adjustment 2 Personal application 3 Work arrangement 4 Violation of laws and disciplines 5 Performance not up to standard 6 Personal reasons 7 Not suitable for the current position',
  `old_dept` int(0) NULL DEFAULT NULL COMMENT 'Original department',
  `new_dept` int(0) NULL DEFAULT NULL COMMENT 'New department',
  `old_post` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Original post',
  `new_post` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'New post',
  `old_post_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'New rank',
  `new_post_level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'New rank',
  `old_work_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Original work place',
  `new_work_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'New workplace',
  `old_parent_id` int(0) NULL DEFAULT NULL COMMENT 'Former direct superior',
  `new_parent_id` int(0) NULL DEFAULT NULL COMMENT 'New direct superior',
  `probation` int(0) NULL DEFAULT NULL COMMENT 'Probation',
  `effect_time` datetime(0) NULL DEFAULT NULL COMMENT 'Effective time',
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Employee position/position change records' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_change_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_contacts
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_contacts`;
CREATE TABLE `wk_hrm_employee_contacts`  (
  `contacts_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NULL DEFAULT NULL,
  `contacts_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Contact name',
  `relation` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Relation',
  `contacts_phone` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Contact number',
  `contacts_work_unit` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Contact work unit',
  `contacts_post` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Contact Child Jobs',
  `contacts_address` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Contact address',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`contacts_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Employee Contact' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_contacts
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_contacts_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_contacts_data`;
CREATE TABLE `wk_hrm_employee_contacts_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `label_group` int(0) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `field_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Field value',
  `field_value_desc` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Field value description',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `contacts_id` int(0) NOT NULL COMMENT 'contacts_id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Customer Extended Field Data Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_contacts_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_contract
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_contract`;
CREATE TABLE `wk_hrm_employee_contract`  (
  `contract_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NOT NULL,
  `contract_num` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Contract Number',
  `contract_type` int(0) NULL DEFAULT NULL COMMENT '1. Fixed-term labor contract 2. Unfixed-term labor contract 3. Labor contract with a period of time when certain work tasks have been completed 4. Internship agreement 5. Labor contract 6. Reemployment agreement 7. Labor dispatch contract 8. Secondment contract 9. Others',
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `term` int(0) NULL DEFAULT NULL COMMENT 'The term',
  `status` int(0) NULL DEFAULT NULL COMMENT 'Contract status 0 not executed 1 executed, 2 expired,',
  `sign_company` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Contracting company',
  `sign_time` datetime(0) NULL DEFAULT NULL COMMENT 'Contract signing date',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Remark',
  `is_expire_remind` int(0) NULL DEFAULT NULL COMMENT 'Expiration reminder 0 No 1 Yes',
  `sort` int(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `batch_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`contract_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Employee contract' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_contract
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_data
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_data`;
CREATE TABLE `wk_hrm_employee_data`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `field_id` int(0) NOT NULL,
  `label_group` int(0) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Field Name',
  `field_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Field value',
  `field_value_desc` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Field value description',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `employee_id` int(0) NOT NULL COMMENT 'employee_id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Customer Extended Field Data Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_data
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_education_experience
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_education_experience`;
CREATE TABLE `wk_hrm_employee_education_experience`  (
  `education_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NULL DEFAULT NULL,
  `education` int(0) NULL DEFAULT NULL COMMENT 'Education 1 primary school, 2 junior high school, 3 technical secondary school, 4 secondary vocational school, 5 technical secondary school, 6 high school, 7 junior college, 8 undergraduate, 9 master, 10 doctor, 11 postdoctoral fellow, 12 others',
  `graduate_school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Graduated school',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Specialized',
  `admission_time` datetime(0) NULL DEFAULT NULL COMMENT 'Admission time',
  `graduation_time` datetime(0) NULL DEFAULT NULL COMMENT 'Graduation time',
  `teaching_methods` int(0) NULL DEFAULT NULL COMMENT 'Teaching methods 1 full-time, 2 adult education, 3 distance education, 4 self-study exams, 5 others',
  `is_first_degree` int(0) NULL DEFAULT NULL COMMENT 'First degree or not 0 No 1 Yes',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`education_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Staff education experience' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_education_experience
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_field
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_field`;
CREATE TABLE `wk_hrm_employee_field`  (
  `field_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Custom field English identification',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Field Name',
  `type` int(0) NOT NULL DEFAULT 1 COMMENT 'Field Type 1 Single-line text 2 Multi-line text 3 Single choice 4 Date 5 Number 6 Decimal 7 Mobile phone 8 File 9 Multiple choice 10 Date and time 11 Email 12 Origin area',
  `component_type` int(0) NOT NULL DEFAULT 1 COMMENT 'Association table type 0 No association required 1 hrm employee 2 hrm department 3 hrm position 4 system user 5 system department 6 recruitment channel',
  `label` int(0) NULL DEFAULT NULL COMMENT 'Label 1 Personal Information 2 Job Information 3 Contract 4 Salary Social Security',
  `label_group` int(0) NOT NULL COMMENT 'Label grouping* 1 Employee Personal Information 2 Communication Information 3 Educational Experience 4 Work Experience 5 Certificate/Certificate 6 Training Experience 7 Contact Person\n * 11 Job Information 12 Resignation Information\n * 21 Contract Information\n * 31 Salary Card Information 32 Social Security information',
  `remark` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field Description',
  `input_tips` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Input hint',
  `max_length` int(0) NULL DEFAULT NULL COMMENT 'The maximum length',
  `default_value` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Defaults',
  `is_unique` int(0) NULL DEFAULT 0 COMMENT 'Is it unique 1 yes 0 no',
  `is_null` int(0) NULL DEFAULT 0 COMMENT 'Required 1 Yes 0 No',
  `sorting` int(0) NULL DEFAULT 1 COMMENT 'Sort from smallest to largest',
  `options` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'If the type is an option, it cannot be empty here, use the kv format',
  `is_fixed` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether the field is fixed 0 No 1 Yes',
  `operating` int(0) NULL DEFAULT 255 COMMENT 'Operation authority',
  `is_hidden` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to hide 0 not to hide 1 to hide',
  `is_update_value` int(0) NULL DEFAULT 1 COMMENT 'Can the value be modified 0 No 1 Yes',
  `is_head_field` int(0) NULL DEFAULT 0 COMMENT 'Whether to display at the head of the list 0 No 1 Yes',
  `is_import_field` int(0) NULL DEFAULT 0 COMMENT 'Whether to import fields 0 no 1 yes',
  `is_employee_visible` int(0) NOT NULL DEFAULT 1 COMMENT 'Whether the employee is visible 0 No 1 Yes',
  `is_employee_update` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether employees can modify 0 No 1 Yes 2 Disable No',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Last Modified',
  `style_percent` int(0) NULL DEFAULT 50 COMMENT 'Style percentage %',
  `precisions` int(0) NULL DEFAULT NULL COMMENT 'Precision, maximum allowed decimal places',
  `form_position` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Form positioning Coordinate format: 1,1',
  `max_num_restrict` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Maximum value limit',
  `min_num_restrict` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'The minimum value of the limit',
  `form_assist_id` int(0) NULL DEFAULT NULL COMMENT 'Form auxiliary id, front-end generated',
  PRIMARY KEY (`field_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1161 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Custom field table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_field
-- ----------------------------
INSERT INTO `wk_hrm_employee_field` VALUES (1103, 'employee_name', 'Name', 1, 0, 1, 1, '', '', 50, '', 0, 1, 1, '', 1, 48, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1104, 'flied_hukzra', 'English name', 1, 1, 1, 1, '', '', 50, '', 0, 0, 2, '', 0, 255, 0, 1, 0, 1, 1, 1, '2021-08-02 14:04:40', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1105, 'mobile', 'Cell phone', 7, 0, 1, 1, '', '11-digit mobile phone number', 11, '', 1, 1, 3, '', 1, 48, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1106, 'id_type', 'Type of certificate', 3, 0, 1, 1, '', '', 1, '', 0, 0, 4, '[{\"name\":\"ID card\",\"value\":1},{\"name\":\"Passport\",\"value\":2},{\"name\":\"Other\",\"value\":3}]', 1, 48, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1107, 'id_number', 'ID number', 1, 0, 1, 1, '', '', 255, '', 0, 0, 5, '', 1, 48, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1108, 'sex', 'Gender', 3, 0, 1, 1, '', '', 1, '', 0, 0, 6, '[{\"name\":\"man\",\"value\":1},{\"name\":\"woman\",\"value\":2}]', 1, 48, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1109, 'date_of_birth', 'Date of birth', 4, 0, 1, 1, '', '', 1, '', 0, 0, 7, '', 1, 48, 0, 0, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1110, 'birthday_type', 'Birthday type', 3, 0, 1, 1, '', '', 50, '', 0, 0, 8, '[{\"name\":\"Gregorian\",\"value\":1},{\"name\":\"Lunar\",\"value\":2}]', 1, 48, 0, 1, 0, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1111, 'birthday', 'Birthday', 1, 0, 1, 1, '', 'Example : 0323', 50, '', 0, 0, 9, '', 1, 62, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1112, 'age', 'Age', 5, 0, 1, 1, '', '', 50, '', 0, 0, 10, '', 1, 48, 0, 0, 1, 0, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1113, 'flied_bbnpqh', 'Are you married', 3, 0, 1, 1, '', '', 50, '', 0, 0, 11, '[{\"name\":\"Yes\",\"value\":\"Yes\"},{\"name\":\"No\",\"value\":\"No\"}]', 0, 191, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:40', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1114, 'flied_dxnkqj', 'Has it been bred', 3, 0, 1, 1, '', '', 50, '', 0, 0, 12, '[{\"name\":\"Yes\",\"value\":\"Yes\"},{\"name\":\"No\",\"value\":\"No\"}]', 0, 191, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:40', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1115, 'country', 'Country / region', 3, 0, 1, 1, '', '', 50, '', 0, 0, 13, '[{\"name\":\"Korea\",\"value\":\"Korea\"},{\"name\":\"USA\",\"value\":\"USA\"}]', 1, 62, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1116, 'nation', 'Nationality', 3, 0, 1, 1, '', '', 50, '', 0, 0, 14, '[{\"name\":\"Korean\",\"value\":\"Korean\"},{\"name\":\"American\",\"value\":\"American\"},{\"name\":\"Chinese\",\"value\":\"Chinese\"},{\"name\":\"Japanese\",\"value\":\"Japanese\"},{\"name\":\"Vietnamese\",\"value\":\"Vietnamese\"},{\"name\":\"Thai\",\"value\":\"Thai\"}]', 1, 62, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1117, 'flied_luxpii', 'Political status', 1, 0, 1, 1, '', '', 50, '', 0, 0, 15, '', 0, 62, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:40', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1118, 'native_place', 'Hometown', 54, 7, 1, 1, '', '', 50, '', 0, 0, 16, '', 1, 62, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1119, 'address', 'Domicile', 1, 0, 1, 1, '', '', 255, '', 0, 0, 17, '', 1, 190, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1120, 'flied_mosheh', 'Health status', 1, 0, 1, 1, '', '', 255, '', 0, 0, 18, '', 0, 254, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:40', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1121, 'highest_education', 'Highest education', 3, 0, 1, 1, '', '', 50, '', 0, 0, 19, '[{\"name\":\"Primary\",\"value\":1},{\"name\":\"Junior\",\"value\":2},{\"name\":\"Secondary College\",\"value\":3},{\"name\":\"Secondary College\",\"value\":4},{\"name\":\"Polytechnic\",\"value\":5},{\"name\":\"High School\",\"value\":6},{\"name\":\"Junior\",\"value\":7},{\"name\":\"Undergraduate\",\"value\":8},{\"name\":\"Master\",\"value\":9 },{\"name\":\"Doctor\",\"value\":10},{\"name\":\"Postdoc\",\"value\":11},{\"name\":\"Other\",\"value\":12}]', 1, 62, 0, 1, 1, 1, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1122, 'contacts_name', 'Contact name', 1, 0, 1, 7, '', '', 255, '', 0, 1, 1, '', 1, 62, 0, 1, 0, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1123, 'relation', 'Relation', 1, 0, 1, 7, '', '', 255, '', 0, 0, 1, '', 1, 62, 0, 1, 0, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1124, 'contacts_phone', 'Contact number', 7, 0, 1, 7, '', '', 255, '', 0, 0, 1, '', 1, 62, 0, 1, 0, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1125, 'contacts_work_unit', 'Contact work unit', 1, 0, 1, 7, '', '', 255, '', 0, 0, 1, '', 1, 62, 0, 1, 0, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1126, 'contacts_post', 'Contact title', 1, 0, 1, 7, '', '', 255, '', 0, 0, 1, '', 1, 62, 0, 1, 0, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1127, 'contacts_address', 'Contact address', 1, 0, 1, 7, '', '', 255, '', 0, 0, 1, '', 1, 62, 0, 1, 0, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1128, 'flied_kwbova', 'Cellphone number', 7, 0, 1, 2, '', '', 255, '', 0, 0, 1, '', 0, 255, 0, 1, 0, 0, 1, 1, '2021-08-02 14:04:40', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1129, 'email', 'Personal Mailbox', 14, 0, 1, 2, '', '', 255, '', 0, 0, 2, '', 1, 48, 0, 1, 0, 0, 1, 1, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1132, 'flied_mhktwv', 'Living', 1, 0, 1, 2, '', '', 255, '', 0, 0, 5, '', 0, 255, 0, 1, 0, 0, 1, 1, '2021-08-02 14:04:40', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1133, 'flied_qppedz', 'Emergency contact', 1, 0, 1, 2, '', '', 255, '', 0, 0, 6, '', 0, 255, 0, 1, 0, 0, 1, 1, '2021-08-02 14:04:40', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1134, 'flied_dumavf', 'Emergency contact number', 7, 0, 1, 2, '', '', 255, '', 0, 0, 7, '', 0, 255, 0, 1, 0, 0, 1, 1, '2021-08-02 14:04:40', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1135, 'job_number', 'Job number', 1, 0, 2, 11, '', '', 50, '', 1, 1, 4, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1136, 'entry_time', 'Entry date', 4, 0, 2, 11, '', '', 50, '', 0, 1, 1, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1137, 'dept_id', 'Department', 12, 2, 2, 11, '', '', 50, '', 0, 0, 5, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1138, 'post', 'Post', 1, 0, 2, 11, '', '', 50, '', 0, 0, 7, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1139, 'parent_id', 'Direct superior', 10, 1, 2, 11, '', '', 50, '', 0, 0, 6, '', 1, 48, 0, 1, 1, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1140, 'post_level', 'Rank', 1, 0, 2, 11, '', '', 50, '', 0, 0, 8, '', 1, 62, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1141, 'work_city', 'Work city', 40, 0, 2, 11, '', '', 255, '', 0, 0, 11, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1142, 'work_address', 'Work place', 1, 0, 2, 11, '', '', 255, '', 0, 0, 9, '', 1, 62, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1143, 'work_detail_address', 'Detailed work location', 1, 0, 2, 11, '', '', 255, '', 0, 0, 10, '', 1, 62, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1144, 'employment_forms', 'Employment form', 3, 0, 2, 11, '', '', 255, '', 0, 1, 13, '[{\"name\":\"formal\",\"value\":1},{\"name\":\"informal\",\"value\":2}]', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1145, 'probation', 'Probation', 3, 0, 2, 11, '', '', 50, '', 0, 0, 2, '[{\"name\":\"No trial period\",\"value\":0},{\"name\":\"1 month\",\"value\":1},{\"name\":\"2 months\",\"value\":2},{\"name\":\"3 months\",\"value\":3},{\"name\":\"4 months\",\"value\":4},{\"name\":\"5 months\",\"value\":5},{\"name\":\"6 months\",\"value\":6}]', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1146, 'become_time', 'Correction date', 4, 0, 2, 11, '', '', 50, '', 0, 0, 3, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1147, 'company_age_start_time', 'Company start date', 4, 0, 2, 11, '', '', 255, '', 0, 0, 14, '', 1, 50, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1148, 'company_age', 'Company days', 5, 0, 2, 11, '', '', 255, '', 0, 0, 15, '', 1, 48, 0, 0, 1, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1149, 'channel_id', 'Recruitment channels', 55, 6, 2, 11, '', '', 255, '', 0, 0, 12, '', 1, 62, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1150, 'status', 'Employee status', 3, 0, 2, 11, '', '', 255, '', 0, 0, 13, '[{\"name\":\"official\",\"value\":1},{\"name\":\"trial\",\"value\":2},{\"name\":\"Intern\",\"value\":3},{\"name\":\"Part-time\",\"value\":4},{\"name\":\"Labor\",\"value\":5},{\"name\":\"Consultant\",\"value\":6},{\"name\":\"Re-employment\",\"value\":7},{\"name\":\"Outsourcing\",\"value\":8},{\"name\":\"To be resigned\",\"value\":9}, {\"name\":\"Resigned\",\"value\":10}]', 1, 48, 0, 1, 1, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1151, 'contract_type', 'Type of contract', 3, 0, 3, 21, '', '', 255, '', 0, 0, 1, '[{\"name\":\"Fixed term labor contract\",\"value\":1},{\"name\":\"Unfixed term labor contract\",\"value\":2 },{\"name\":\"A labor contract with a certain period of work completed\",\"value\":3},{\"name\":\"Internship agreement\",\"value\":4},{\"name\":\"Labor Service Contract\",\"value\":5},{\"name\":\"Reemployment Agreement\",\"value\":6 },{\"name\":\"Labor dispatch contract\",\"value\":7},{\"name\":\"Secondment contract\",\"value\":8},{\"name\":\"Other\",\"value\":9}]', 1, 48, 0, 1, 1, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1152, 'start_time', 'Current contract start date', 4, 0, 3, 21, '', '', 255, '', 0, 0, 2, '', 1, 48, 0, 1, 1, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1153, 'end_time', 'Current contract end date', 4, 0, 3, 21, '', '', 255, '', 0, 0, 3, '', 1, 48, 0, 1, 1, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1154, 'term', 'Current contract period', 5, 0, 3, 21, '', '', 255, '', 0, 0, 4, '', 1, 48, 0, 1, 1, 0, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1155, 'salary_card_num', 'Payroll card number', 1, 0, 4, 31, '', '', 255, '', 0, 0, 1, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1156, 'account_opening_city', 'Salary card account opening city', 1, 0, 4, 31, '', '', 255, '', 0, 0, 2, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1157, 'bank_name', 'Bank name', 1, 0, 4, 31, '', '', 255, '', 0, 0, 4, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1158, 'opening_bank', 'Payroll card bank', 1, 0, 4, 31, '', '', 255, '', 0, 0, 5, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1159, 'social_security_num', 'Personal social security account', 1, 0, 4, 32, '', '', 255, '', 0, 0, 1, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `wk_hrm_employee_field` VALUES (1160, 'accumulation_fund_num', 'Personal Provident Fund Account', 1, 0, 4, 32, '', '', 255, '', 0, 0, 2, '', 1, 48, 0, 1, 1, 1, 1, 0, '2021-08-02 14:04:01', 50, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for wk_hrm_employee_field_config
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_field_config`;
CREATE TABLE `wk_hrm_employee_field_config`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Field id',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT 'Sort by field',
  `user_id` bigint(0) NOT NULL DEFAULT 0 COMMENT 'Userid',
  `is_hide` int(0) NOT NULL DEFAULT 1 COMMENT 'Whether to hide 0, not hide 1, hide',
  `width` int(0) NULL DEFAULT NULL COMMENT 'Field width',
  `field_id` int(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT 'Update time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `label`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 431 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Field sort table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_field_config
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_field_manage
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_field_manage`;
CREATE TABLE `wk_hrm_employee_field_manage`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `entry_status` int(0) NOT NULL DEFAULT 1 COMMENT 'Onboarding Status 1 Active 2 Onboarding',
  `field_id` int(0) NULL DEFAULT NULL COMMENT 'Field id',
  `field_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Field Name',
  `is_manage_visible` int(0) NOT NULL DEFAULT 1 COMMENT 'Admin visible or not 0 No 1 Yes 2 Disable No 3 Disable Yes',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 239 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Custom field management table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_field_manage
-- ----------------------------
INSERT INTO `wk_hrm_employee_field_manage` VALUES (113, 1, 1103, 'employee_name', 'Name', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (114, 1, 1104, 'flied_hukzra', 'English name', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (115, 1, 1105, 'mobile', 'Cell phone', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (116, 1, 1106, 'id_type', 'Type of certificate', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (117, 1, 1107, 'id_number', 'ID number', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (118, 1, 1108, 'sex', 'Gender', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (119, 1, 1109, 'date_of_birth', 'Date of birth', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (120, 1, 1110, 'birthday_type', 'Birthday type', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (121, 1, 1111, 'birthday', 'Birthday', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (122, 1, 1112, 'age', 'Age', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (123, 1, 1113, 'flied_bbnpqh', 'Are you married', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (124, 1, 1114, 'flied_dxnkqj', 'Has it been bred', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (125, 1, 1115, 'country', 'Country / region', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (126, 1, 1116, 'nation', 'Nationality', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (127, 1, 1117, 'flied_luxpii', 'Political status', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (128, 1, 1118, 'native_place', 'Hometown', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (129, 1, 1119, 'address', 'Domicile', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (130, 1, 1120, 'flied_mosheh', 'Health status', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (131, 1, 1121, 'highest_education', 'Highest education', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (132, 1, 1135, 'job_number', 'Job number', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (133, 1, 1136, 'entry_time', 'Entry date', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (134, 1, 1137, 'dept_id', 'Department', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (135, 1, 1138, 'post', 'Post', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (136, 1, 1139, 'parent_id', 'Direct superior', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (137, 1, 1140, 'post_level', 'Rank', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (138, 1, 1141, 'work_city', 'Work city', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (139, 1, 1142, 'work_address', 'Work place', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (140, 1, 1143, 'work_detail_address', 'Detailed work location', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (141, 1, 1144, 'employment_forms', 'Employment form', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (142, 1, 1145, 'probation', 'Probation', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (143, 1, 1147, 'company_age_start_time', 'Company start date', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (144, 1, 1149, 'channel_id', 'Recruitment channels', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (145, 1, 1150, 'status', 'Employee status', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (176, 2, 1103, 'employee_name', 'Name', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (177, 2, 1104, 'flied_hukzra', 'English name', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (178, 2, 1105, 'mobile', 'Cell phone', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (179, 2, 1106, 'id_type', 'Type of certificate', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (180, 2, 1107, 'id_number', 'ID number', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (181, 2, 1108, 'sex', 'Gender', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (182, 2, 1109, 'date_of_birth', 'Date of birth', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (183, 2, 1110, 'birthday_type', 'Birthday type', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (184, 2, 1111, 'birthday', 'Birthday', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (185, 2, 1112, 'age', 'Age', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (186, 2, 1113, 'flied_bbnpqh', 'Are you married', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (187, 2, 1114, 'flied_dxnkqj', 'Has it been bred', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (188, 2, 1115, 'country', 'Country / region', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (189, 2, 1116, 'nation', 'Nationality', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (190, 2, 1117, 'flied_luxpii', 'Political status', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (191, 2, 1118, 'native_place', 'Hometown', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (192, 2, 1119, 'address', 'Domicile', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (193, 2, 1120, 'flied_mosheh', 'Health status', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (194, 2, 1121, 'highest_education', 'Highest education', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (195, 2, 1135, 'job_number', 'Job number', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (196, 2, 1136, 'entry_time', 'Entry date', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (197, 2, 1137, 'dept_id', 'Department', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (198, 2, 1138, 'post', 'Post', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (199, 2, 1139, 'parent_id', 'Direct superior', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (200, 2, 1140, 'post_level', 'Rank', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (201, 2, 1141, 'work_city', 'Work city', 1);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (202, 2, 1142, 'work_address', 'Work place', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (203, 2, 1143, 'work_detail_address', 'Detailed work location', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (204, 2, 1144, 'employment_forms', 'Employment form', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (205, 2, 1145, 'probation', 'Probation', 3);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (206, 2, 1147, 'company_age_start_time', 'Company start date', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (207, 2, 1149, 'channel_id', 'Recruitment channels', 0);
INSERT INTO `wk_hrm_employee_field_manage` VALUES (208, 2, 1150, 'status', 'Employee status', 3);

-- ----------------------------
-- Table structure for wk_hrm_employee_file
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_file`;
CREATE TABLE `wk_hrm_employee_file`  (
  `employee_file_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NOT NULL COMMENT 'Employee id',
  `file_id` bigint(0) NOT NULL COMMENT 'Admin module attachment id',
  `type` int(0) NULL DEFAULT NULL COMMENT '1 Employee Basic Information 2 Employee Profile Information 3 Employee Resignation Information',
  `sub_type` int(0) NOT NULL COMMENT '11. Original ID card 12, education certificate 13, personal ID photo 14, copy of ID card 15, salary bank card 16, social security card 17, provident fund card 18, award certificate 19, other 21, labor contract 22, resume 23, Entry Registration Form 24, Entry Physical Examination Form 25, Resignation Certificate 26, Regular Application Form 27, Other\n31, Resignation Approval 32, Resignation Certificate 33, Other\n',
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'Creation time',
  PRIMARY KEY (`employee_file_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee Attachment Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_file
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_quit_info
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_quit_info`;
CREATE TABLE `wk_hrm_employee_quit_info`  (
  `quit_info_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NULL DEFAULT NULL,
  `plan_quit_time` datetime(0) NULL DEFAULT NULL COMMENT 'Planned departure date',
  `apply_quit_time` datetime(0) NULL DEFAULT NULL COMMENT 'Application for resignation date',
  `salary_settlement_time` datetime(0) NULL DEFAULT NULL COMMENT 'Payroll Date',
  `quit_type` int(0) NULL DEFAULT NULL COMMENT 'Type of resignation 1 Active resignation 2 Passive resignation 3 Retirement',
  `quit_reason` int(0) NULL DEFAULT NULL COMMENT 'Reasons for resignation 1 Family reasons 2 Physical reasons 3 Salary reasons 4 Traffic inconvenience 5 Work pressure 6 Management problems 7 No promotion opportunities 8 Career planning 9 Abandoning renewals when the contract expires 10 Other personal reasons 11 Dismissal during the probation period 12 Violation of company regulations 13 Organizational adjustment/ 14 Layoffs 14 Dismissal for failure to meet performance standards 15 No renewal of contract due to expiration 16 Passive resignation for other reasons',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Remark',
  `old_status` int(0) NULL DEFAULT NULL COMMENT 'Pre-retirement status',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`quit_info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Resignation information' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_quit_info
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_salary_card
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_salary_card`;
CREATE TABLE `wk_hrm_employee_salary_card`  (
  `salary_card_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NOT NULL,
  `salary_card_num` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Payroll card number',
  `account_opening_city` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Account opening city',
  `bank_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Bank name',
  `opening_bank` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Payroll card bank',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`salary_card_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Employee Payroll Card Information' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_salary_card
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_social_security_info
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_social_security_info`;
CREATE TABLE `wk_hrm_employee_social_security_info`  (
  `social_security_info_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NULL DEFAULT NULL,
  `is_first_social_security` int(0) NULL DEFAULT NULL COMMENT 'Whether to pay social security for the first time 0 No 1 Yes',
  `is_first_accumulation_fund` int(0) NULL DEFAULT NULL COMMENT 'Whether to pay the provident fund for the first time 0 No 1 Yes',
  `social_security_num` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Social security number',
  `accumulation_fund_num` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Provident fund account',
  `social_security_start_month` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'The starting month of insurance participation (2020.05)',
  `scheme_id` int(0) NULL DEFAULT NULL COMMENT 'Insurance plan',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`social_security_info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Employee Provident Fund Information' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_social_security_info
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_training_experience
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_training_experience`;
CREATE TABLE `wk_hrm_employee_training_experience`  (
  `training_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NULL DEFAULT NULL,
  `training_course` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Training Courses',
  `training_organ_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Training institution name',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Training start time',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT 'Training end time',
  `training_duration` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Training time',
  `training_results` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Training results',
  `training_certificate_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Training course name',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Remark',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL COMMENT 'Sort',
  PRIMARY KEY (`training_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Training experience' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_training_experience
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_employee_work_experience
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_employee_work_experience`;
CREATE TABLE `wk_hrm_employee_work_experience`  (
  `work_exp_id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NOT NULL,
  `work_unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Employer',
  `post` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Job title',
  `work_start_time` datetime(0) NULL DEFAULT NULL COMMENT 'Work start time',
  `work_end_time` datetime(0) NULL DEFAULT NULL COMMENT 'Work end time',
  `leaving_reason` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Reason for leaving',
  `witness` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Witness',
  `witness_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'The mobile phone number of the witness',
  `work_remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'Work notes',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `sort` int(0) NULL DEFAULT NULL COMMENT 'Sort',
  PRIMARY KEY (`work_exp_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Employee work experience' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_employee_work_experience
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_field_extend
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_field_extend`;
CREATE TABLE `wk_hrm_field_extend`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Primary key ID',
  `parent_field_id` int(0) NOT NULL COMMENT 'Corresponding main field id',
  `field_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Custom field English identification',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Field Name',
  `type` int(0) NOT NULL DEFAULT 1 COMMENT 'Field Type',
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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Extended custom field table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_field_extend
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_insurance_month_emp_project_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_insurance_month_emp_project_record`;
CREATE TABLE `wk_hrm_insurance_month_emp_project_record`  (
  `emp_project_record_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Insured project record id',
  `i_emp_record_id` int(0) NOT NULL COMMENT 'Insured employee record id',
  `project_id` int(0) NULL DEFAULT NULL COMMENT 'Background configuration project id',
  `type` int(0) NOT NULL COMMENT '1 Base of endowment insurance 2 Base of medical insurance 3 Base of unemployment insurance 4 Base of work-related injury insurance 5 Base of maternity insurance 6 Supplementary medical insurance for serious illness 7 Supplementary pension insurance 8 Provident fund',
  `project_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Project name',
  `default_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Default base',
  `corporate_proportion` decimal(5, 2) NULL DEFAULT 0.00 COMMENT 'Company ratio',
  `personal_proportion` decimal(5, 2) NULL DEFAULT 0.00 COMMENT 'Personal ratio',
  `corporate_amount` decimal(10, 2) NOT NULL COMMENT 'Amount paid by the company',
  `personal_amount` decimal(10, 2) NOT NULL COMMENT 'Personal payment amount',
  PRIMARY KEY (`emp_project_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee Monthly Insurance Item Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_insurance_month_emp_project_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_insurance_month_emp_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_insurance_month_emp_record`;
CREATE TABLE `wk_hrm_insurance_month_emp_record`  (
  `i_emp_record_id` int(0) NOT NULL AUTO_INCREMENT,
  `i_record_id` int(0) NULL DEFAULT NULL COMMENT 'Generate social security id every month',
  `employee_id` int(0) NULL DEFAULT NULL COMMENT 'Employee id',
  `scheme_id` int(0) NULL DEFAULT NULL COMMENT 'Social security plan id',
  `year` int(0) NULL DEFAULT NULL COMMENT 'Year',
  `month` int(0) NULL DEFAULT NULL COMMENT 'Moon',
  `personal_insurance_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Personal social security amount',
  `personal_provident_fund_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Personal Provident Fund Amount',
  `corporate_insurance_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Company social security amount',
  `corporate_provident_fund_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Company social security amount',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `status` int(0) NULL DEFAULT 1 COMMENT 'Monthly Social Security Status 0 Suspended 1 Normal',
  PRIMARY KEY (`i_emp_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee monthly social security records' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_insurance_month_emp_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_insurance_month_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_insurance_month_record`;
CREATE TABLE `wk_hrm_insurance_month_record`  (
  `i_record_id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Report name',
  `year` int(0) NULL DEFAULT NULL COMMENT 'Years',
  `month` int(0) NULL DEFAULT NULL COMMENT 'Month',
  `num` int(0) NULL DEFAULT NULL COMMENT 'Number of participants',
  `status` int(0) NULL DEFAULT 0 COMMENT 'Monthly Social Security Status 0 Unfiled 1 Filed',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`i_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Monthly social security records' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_insurance_month_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_insurance_project
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_insurance_project`;
CREATE TABLE `wk_hrm_insurance_project`  (
  `project_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Project id',
  `scheme_id` int(0) NOT NULL COMMENT 'Insurance plan id',
  `type` int(0) NOT NULL COMMENT '1 Endowment insurance base 2 Medical insurance base 3 Unemployment insurance base 4 Work-related injury insurance base 5 Maternity insurance base 6 Supplementary medical insurance for serious illness 7 Supplementary pension insurance 8 Disability insurance 9 Self-defined social security 10 Provident fund 11 Customized provident fund',
  `project_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Project name',
  `default_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Default base',
  `corporate_proportion` decimal(5, 2) NULL DEFAULT 0.00 COMMENT 'Company ratio',
  `personal_proportion` decimal(5, 2) NULL DEFAULT 0.00 COMMENT 'Personal ratio',
  `corporate_amount` decimal(10, 2) NOT NULL COMMENT 'Amount paid by the company',
  `personal_amount` decimal(10, 2) NOT NULL COMMENT 'Personal payment amount',
  `is_del` int(0) NOT NULL DEFAULT 1 COMMENT '1 delete 0 use',
  PRIMARY KEY (`project_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Insured item list' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_insurance_project
-- ----------------------------
INSERT INTO `wk_hrm_insurance_project` VALUES (13, 3, 1, '', 2464.00, 16.00, 8.00, 394.24, 197.12, 1);
INSERT INTO `wk_hrm_insurance_project` VALUES (14, 3, 2, '', 2464.00, 8.00, 2.00, 197.12, 49.28, 1);
INSERT INTO `wk_hrm_insurance_project` VALUES (15, 3, 3, '', 2464.00, 0.20, 0.00, 4.93, 0.00, 1);
INSERT INTO `wk_hrm_insurance_project` VALUES (16, 3, 4, '', 2464.00, 0.70, 0.30, 17.25, 7.39, 1);
INSERT INTO `wk_hrm_insurance_project` VALUES (17, 3, 5, '', 2464.00, 1.00, 0.00, 24.64, 0.00, 1);
INSERT INTO `wk_hrm_insurance_project` VALUES (18, 3, 10, '', 3057.00, 10.00, 10.00, 306.00, 306.00, 1);

-- ----------------------------
-- Table structure for wk_hrm_insurance_scheme
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_insurance_scheme`;
CREATE TABLE `wk_hrm_insurance_scheme`  (
  `scheme_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Social security plan id',
  `scheme_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Scheme name',
  `city` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Insured city',
  `house_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Type of household registration',
  `scheme_type` int(0) NOT NULL COMMENT 'Enrollment Type 1 Scale 2 Amount',
  `is_del` int(0) NOT NULL DEFAULT 0 COMMENT '1 delete 0 use',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  PRIMARY KEY (`scheme_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Social Security Program Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_insurance_scheme
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_notes
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_notes`;
CREATE TABLE `wk_hrm_notes`  (
  `notes_id` int(0) NOT NULL AUTO_INCREMENT,
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `employee_id` int(0) NULL DEFAULT NULL,
  `reminder_time` datetime(0) NOT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `create_user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`notes_id`) USING BTREE,
  INDEX `wk_hrm_notes_employee_id_index`(`employee_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Memo' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_notes
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_recruit_candidate
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_recruit_candidate`;
CREATE TABLE `wk_hrm_recruit_candidate`  (
  `candidate_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Candidate id',
  `candidate_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Candidate name',
  `mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Cell phone',
  `sex` int(0) NOT NULL COMMENT 'Gender 1 male 2 female',
  `age` int(0) NULL DEFAULT NULL COMMENT 'Age',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Mail',
  `post_id` int(0) NOT NULL COMMENT 'Job id',
  `stage_num` int(0) NOT NULL DEFAULT 0 COMMENT 'Interview rounds',
  `work_time` int(0) NULL DEFAULT NULL COMMENT 'Working years',
  `education` int(0) NOT NULL COMMENT 'Education 1 primary school 2 junior high school 3 high school 4 college 5 undergraduate 6 master 7 doctor',
  `graduate_school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Graduated school',
  `latest_work_place` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Recent work unit',
  `channel_id` int(0) NULL DEFAULT NULL COMMENT 'Recruitment channels',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `status` int(0) NULL DEFAULT 1 COMMENT 'Candidate status 1 New candidate 2 Passed the primary election 3 Arranged the interview 4 Passed the interview 5 Offered 6 Pending entry 7 Eliminated 8 Enrolled',
  `eliminate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Reason for elimination',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator id',
  `status_update_time` datetime(0) NULL DEFAULT NULL COMMENT 'Status update time',
  `entry_time` datetime(0) NULL DEFAULT NULL COMMENT 'Entry Time',
  `batch_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Batch id',
  PRIMARY KEY (`candidate_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Recruitment Candidate Form' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_recruit_candidate
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_recruit_channel
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_recruit_channel`;
CREATE TABLE `wk_hrm_recruit_channel`  (
  `channel_id` int(0) NOT NULL AUTO_INCREMENT,
  `is_sys` int(0) NULL DEFAULT 0 COMMENT 'Whether the system defaults 0 No 1 Yes',
  `status` int(0) NULL DEFAULT 1 COMMENT 'Status 0 Disable 1 Enable',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`channel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 281 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Recruitment channel table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_recruit_channel
-- ----------------------------
INSERT INTO `wk_hrm_recruit_channel` VALUES (274, 1, 1, 'Boss direct employment');
INSERT INTO `wk_hrm_recruit_channel` VALUES (275, 1, 1, 'Network');
INSERT INTO `wk_hrm_recruit_channel` VALUES (277, 1, 1, 'Internal recommendation');
INSERT INTO `wk_hrm_recruit_channel` VALUES (278, 1, 1, 'Employee Referral');
INSERT INTO `wk_hrm_recruit_channel` VALUES (280, 1, 1, 'Other');

-- ----------------------------
-- Table structure for wk_hrm_recruit_interview
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_recruit_interview`;
CREATE TABLE `wk_hrm_recruit_interview`  (
  `interview_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Interview id',
  `candidate_id` int(0) NOT NULL COMMENT 'Candidate id',
  `type` int(0) NULL DEFAULT NULL COMMENT 'Interview method 1On-site interview 2Phone interview 3Video interview',
  `stage_num` int(0) NOT NULL DEFAULT 1 COMMENT 'Interview rounds',
  `interview_employee_id` int(0) NULL DEFAULT NULL COMMENT 'Interviewer id',
  `other_interview_employee_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Other interviewers',
  `interview_time` datetime(0) NOT NULL COMMENT 'Interview time',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Interview address',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `result` int(0) NULL DEFAULT 1 COMMENT 'Interview Status 1 Interview not completed 2 Interview passed 3 Interview failed 4 Interview cancelled',
  `evaluate` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Evaluation',
  `cancel_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Reason for Cancellation',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator id',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  PRIMARY KEY (`interview_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Creation time' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_recruit_interview
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_recruit_post
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_recruit_post`;
CREATE TABLE `wk_hrm_recruit_post`  (
  `post_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Job id',
  `post_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Position Name',
  `dept_id` int(0) NULL DEFAULT NULL COMMENT 'Department id',
  `job_nature` int(0) NULL DEFAULT NULL COMMENT 'Job Type 1 Full Time 2 Internship 3 Part Time',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Translation',
  `recruit_num` int(0) NULL DEFAULT 0 COMMENT 'Number of recruits',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Recruitment reason',
  `work_time` int(0) NULL DEFAULT NULL COMMENT 'Work experience 1 Unlimited 2 Less than one year 3 One to three years 4 Three to five years 5 Five to ten years 6 More than ten years',
  `education_require` int(0) NULL DEFAULT NULL COMMENT 'Educational requirements 1 not limited 2 high school and above 3 college and above 4 bachelor degree and above 5 master degree and above 6 doctorate',
  `min_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Start Salary -1 Negotiable',
  `max_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Closing Salary -1 Negotiable',
  `salary_unit` int(0) NULL DEFAULT NULL COMMENT 'Salary unit 1 ₩/year 2 ₩/month',
  `min_age` int(0) NULL DEFAULT NULL COMMENT 'Minimum age -1 unlimited',
  `max_age` int(0) NULL DEFAULT NULL COMMENT 'Maximum age -1 unlimited',
  `latest_entry_time` datetime(0) NULL DEFAULT NULL COMMENT 'Latest arrival time',
  `owner_employee_id` int(0) NULL DEFAULT NULL COMMENT 'Person in charge id',
  `interview_employee_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Interviewer',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Job description',
  `emergency_level` int(0) NULL DEFAULT NULL COMMENT 'Urgency 1 Urgent 2 Normal',
  `post_type_id` int(0) NULL DEFAULT NULL COMMENT 'Tybe of job',
  `create_time` datetime(0) NOT NULL COMMENT 'Creation time',
  `create_user_id` bigint(0) NOT NULL COMMENT 'Creator id',
  `batch_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Batch id',
  `status` int(0) NULL DEFAULT 1 COMMENT '0 stop 1 enable',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Recruitment list' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_recruit_post
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_recruit_post_type
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_recruit_post_type`;
CREATE TABLE `wk_hrm_recruit_post_type`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `parent_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1041 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'Tybe of job' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_recruit_post_type
-- ----------------------------
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1, 'Senior management', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (2, 'Senior management positions', 1);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (3, 'Senior management positions', 2);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (4, 'President/General Manager/CEO', 2);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (5, 'Vice President/Deputy General Manager/VP', 2);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (6, 'Head of branch/representative office', 2);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (7, 'Regional person in charge (managing multiple branches)', 2);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (8, 'Chief Assistant/CEO Assistant/Chairman Assistant', 2);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (9, 'Partner', 2);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (10, 'Co-founder', 2);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (11, 'Board Secretary', 2);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (12, 'Technology', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (13, 'Backend development', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (14, 'Backend development', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (15, 'Java', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (16, 'C++', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (17, 'PHP', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (18, 'Data mining', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (19, 'C', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (20, 'C#', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (21, '.NET', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (22, 'Hadoop', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (23, 'Python', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (24, 'Delphi', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (25, 'VB', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (26, 'Perl', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (27, 'Ruby', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (28, 'Node.js', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (29, 'Search algorithm', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (30, 'Golang', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (31, 'Recommendation algorithm', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (32, 'Erlang', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (33, 'Algorithm engineer', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (34, 'Voice/Video/Graphics Development', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (35, 'Data collection', 13);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (36, 'Mobile development', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (37, 'UE4', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (38, 'Mobile development', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (39, 'HTML5', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (40, 'Android', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (41, 'iOS', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (42, 'WP', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (43, 'Mobile web front end', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (44, 'Flash development', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (45, 'JavaScript', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (46, 'U3D', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (47, 'COCOS2DX', 36);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (48, 'Test', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (49, 'Test Engineer', 48);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (50, 'Automated test', 48);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (51, 'Function test', 48);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (52, 'Performance Testing', 48);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (53, 'Test development', 48);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (54, 'Mobile test', 48);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (55, 'Game test', 48);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (56, 'Hardware test', 48);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (57, 'Software test', 48);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (58, 'Operation and maintenance/technical support', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (59, 'Operation and Maintenance Engineer', 58);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (60, 'Operation and maintenance engineer', 58);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (61, 'Network Engineer', 58);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (62, 'System engineer', 58);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (63, 'IT technical support', 58);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (64, 'System administrator', 58);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (65, 'Cyber security', 58);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (66, 'System security', 58);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (67, 'DBA', 58);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (68, 'Data', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (69, 'Data', 68);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (70, 'ETL engineer', 68);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (71, 'Database', 68);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (72, 'Data development', 68);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (73, 'Data mining', 68);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (74, 'Data Analyst', 68);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (75, 'Data architect', 68);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (76, 'Algorithm researcher', 68);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (77, 'Project management', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (78, 'Project manager', 77);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (79, 'Project Director', 77);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (80, 'Project assistant', 77);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (81, 'Project specialist', 77);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (82, 'Implementation Consultant', 77);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (83, 'Implementation Engineer', 77);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (84, 'Requirements analysis engineer', 77);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (85, 'Hardware development', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (86, 'Hardware', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (87, 'Embedded', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (88, 'Automation', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (89, 'MCU', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (90, 'Circuit design', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (91, 'Driver development', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (92, 'System integration', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (93, 'FPGA development', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (94, 'DSP development', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (95, 'ARM development', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (96, 'PCB process', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (97, 'RF engineer', 85);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (98, 'Front-end development', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (99, 'Front-end development', 98);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (100, 'Web front end', 98);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (101, 'JavaScript', 98);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (102, 'Flash development', 98);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (103, 'HTML5', 98);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (104, 'Communication', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (105, 'Communication Technology Engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (106, 'Communication R&D Engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (107, 'Data communications engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (108, 'Mobile communication engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (109, 'Telecom Network Engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (110, 'Telecom Switching Engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (111, 'Cable transmission engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (112, 'Radio frequency engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (113, 'Communication power engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (114, 'Communication standardization engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (115, 'Communications Project Specialist', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (116, 'Communications Project Manager', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (117, 'Core network engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (118, 'Communication Test Engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (119, 'Communication equipment engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (120, 'Optical Communication Engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (121, 'Optical Transmission Engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (122, 'Optical Network Engineer', 104);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (123, 'Electronics/Semiconductor', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (124, 'Electronics Engineer', 123);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (125, 'Electrical Engineer', 123);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (126, 'FAE', 123);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (127, 'Electrical design engineer', 123);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (128, 'High-end technical positions', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (129, 'High-end technical positions', 128);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (130, 'Technical manager', 128);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (131, 'Technical director', 128);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (132, 'Test manager', 128);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (133, 'Architect', 128);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (134, 'CTO', 128);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (135, 'Operation and maintenance director', 128);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (136, 'Technical partner', 128);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (137, 'Artificial intelligence', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (138, 'Intelligent driving system engineer', 137);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (139, 'Anti-fraud/risk control algorithm', 137);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (140, 'Artificial intelligence', 137);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (141, 'Natural language processing', 137);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (142, 'Machine learning', 137);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (143, 'Deep learning', 137);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (144, 'Speech Recognition', 137);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (145, 'Image Identification', 137);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (146, 'Algorithm researcher', 137);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (147, 'Sales technical support', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (148, 'Sales technical support', 147);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (149, 'Pre-sales engineer', 147);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (150, 'Postsale engineer', 147);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (151, 'Other technical positions', 12);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (152, 'Other technical positions', 151);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (153, 'Product', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (154, 'Product manager', 153);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (155, 'Hardware product manager', 154);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (156, 'Product manager', 154);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (157, 'Web Product Manager', 154);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (158, 'Mobile Product Manager', 154);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (159, 'Product assistant', 154);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (160, 'Data product manager', 154);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (161, 'E-commerce product manager', 154);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (162, 'Game planning', 154);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (163, 'Product Specialist', 154);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (164, 'Premium Product Jobs', 153);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (165, 'Premium Product Jobs', 164);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (166, 'Product Director', 164);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (167, 'Game producer', 164);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (168, 'Product VP', 164);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (169, 'Other Product Posts', 153);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (170, 'Other Product Posts', 169);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (171, 'Design', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (172, 'Visual design', 171);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (173, 'Cartoonist', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (174, 'Portrait retoucher', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (175, 'Visual design', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (176, 'Visual designer', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (177, 'Web designer', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (178, 'Flash Designer', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (179, 'APP designer', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (180, 'UI designer', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (181, 'Graphic Designer', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (182, 'Art Designer (2D/3D)', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (183, 'Advertising designer', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (184, 'Multimedia designer', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (185, 'Original artist', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (186, 'Game special effects', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (187, 'Game interface designer', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (188, 'Game scene', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (189, 'Game role', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (190, 'Game action', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (191, 'CAD Design/Drawing', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (192, 'Art', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (193, 'Package Design', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (194, 'Designer assistant', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (195, 'Animator', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (196, 'Illustrator', 172);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (197, 'Interactive Design', 171);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (198, 'Interaction Designer', 197);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (199, 'Wireless Interaction Designer', 197);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (200, 'Web interaction designer', 197);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (201, 'Hardware interaction designer', 197);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (202, 'User research', 171);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (203, 'Data Analyst', 202);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (204, 'User researcher', 202);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (205, 'Game numerical planning', 202);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (206, 'UX designer', 202);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (207, 'User Research Manager', 202);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (208, 'User Research Director', 202);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (209, 'High-end design jobs', 171);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (210, 'High-end design jobs', 209);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (211, 'Design Manager/Supervisor', 209);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (212, 'Design Director', 209);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (213, 'Visual design manager', 209);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (214, 'Visual design director', 209);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (215, 'Interaction Design Manager/Supervisor', 209);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (216, 'Interaction Design Director', 209);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (217, 'Non-visual design', 171);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (218, 'Exhibition/Display Design', 217);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (219, 'Non-visual design', 217);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (220, 'Apparel/Textile Design', 217);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (221, 'Industrial design', 217);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (222, 'Cabinet design', 217);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (223, 'Furniture design', 217);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (224, 'Home design', 217);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (225, 'Jewelry Design', 217);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (226, 'Interior design', 217);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (227, 'Display design', 217);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (228, 'Other Design Jobs', 171);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (229, 'Other Design Jobs', 228);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (230, 'Operation', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (231, 'Operation', 230);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (232, 'Data annotation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (233, 'Live broadcast operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (234, 'Vehicle operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (235, 'Cross-border e-commerce operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (236, 'Online store manager', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (237, 'Operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (238, 'User operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (239, 'Product Operations', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (240, 'Data operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (241, 'Content operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (242, 'Event operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (243, 'Business operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (244, 'Category Operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (245, 'Game operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (246, 'Web Promotion', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (247, 'Website operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (248, 'New media operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (249, 'Community operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (252, 'Strategic operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (253, 'Offline expansion of operations', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (254, 'E-commerce operation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (255, 'Operations Assistant/Specialist', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (256, 'Content moderation', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (257, 'Sales operations', 231);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (258, 'Edit', 230);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (259, 'Edit', 258);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (260, 'Deputy Editor', 258);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (261, 'Edit content', 258);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (262, 'Copywriting', 258);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (263, 'Website editor', 258);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (264, 'Reporter', 258);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (265, 'Editing', 258);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (266, 'Customer service', 230);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (267, 'Pre-sales advice', 266);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (268, 'After-sales consultation', 266);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (269, 'Online customer service', 266);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (270, 'Customer Service Manager', 266);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (271, 'Customer Service Specialist/Assistant', 266);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (272, 'Customer service supervisor', 266);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (273, 'Customer Service Director', 266);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (274, 'Telephone customer service', 266);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (275, 'Consulting hotline/call center customer service', 266);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (276, 'High-end Operations Positions', 230);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (277, 'High-end Operations Positions', 276);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (278, 'Editor-in-Chief', 276);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (279, 'Director of Operations', 276);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (280, 'COO', 276);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (281, 'Customer Service Director', 276);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (282, 'Operations Manager/Supervisor', 276);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (283, 'Other operational positions', 230);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (284, 'Other operational positions', 283);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (285, 'Market', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (286, 'Government affairs', 285);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (287, 'Policy Research', 286);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (288, 'Enterprise Party Building', 286);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (289, 'Government Relations', 286);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (290, 'Marketing', 285);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (291, 'Site selection development', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (292, 'Game promotion', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (293, 'Marketing', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (294, 'Market planning', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (295, 'Market consultant', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (296, 'Marketing', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (297, 'SEO', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (298, 'SEM', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (299, 'Business channel', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (300, 'Business data analysis', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (301, 'Event planning', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (302, 'Internet Marketing', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (303, 'Overseas market', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (304, 'APP promotion', 290);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (305, 'PR medium', 285);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (306, 'PR medium', 305);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (307, 'Media manager', 305);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (308, 'Advertising coordination', 305);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (309, 'Brand PR', 305);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (310, 'Media specialist', 305);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (311, 'Event planning and execution', 305);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (312, 'Media planning', 305);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (313, 'Conference and exhibition', 285);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (314, 'Conference and exhibition', 313);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (315, 'Conference Event Sales', 313);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (316, 'Conference event planning', 313);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (317, 'Execution of conference activities', 313);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (318, 'Exhibition event sales', 313);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (319, 'Exhibition event planning', 313);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (320, 'Execution of exhibition activities', 313);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (321, 'Advertise', 285);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (322, 'Advertise', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (323, 'Advertising creative', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (324, 'Art director', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (325, 'Advertising designer', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (326, 'Planning manager', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (327, 'Copywriting', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (328, 'Advertisement making', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (329, 'Media placement', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (330, 'Media cooperation', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (331, 'Media consultant', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (332, 'Ad review', 321);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (333, 'High end market jobs', 285);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (334, 'High end market jobs', 333);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (335, 'Marketing Director', 333);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (336, 'CMO', 333);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (337, 'Director of Public Relations', 333);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (338, 'Media director', 333);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (339, 'Creative Director', 333);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (340, 'Other market positions', 285);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (341, 'Other market positions', 340);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (342, 'HR/Finance/Administration', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (343, 'Human Resources', 342);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (344, 'HR director', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (345, 'Recruitment', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (346, 'HRBP', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (347, 'Human Resource Specialist/Assistant', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (348, 'Training', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (349, 'Salary and benefits', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (350, 'Performance appraisal', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (351, 'Human resources manager', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (352, 'Human Resources VP/CHO', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (353, 'Director of Human Resources', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (354, 'Employee Relations', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (355, 'Organizational Development', 343);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (356, 'Administrative', 342);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (357, 'Administrative Specialist/Assistant', 356);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (358, 'Front desk', 356);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (359, 'Administrative', 356);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (360, 'Assistant manager', 356);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (361, 'Logistics', 356);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (362, 'Business driver', 356);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (363, 'Executive Manager', 356);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (364, 'Executive Director', 356);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (365, 'Finance', 342);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (366, 'Cost', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (367, 'Finance', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (368, 'Accounting', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (369, 'Cashier', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (370, 'Financial consultant', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (371, 'Settlement', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (372, 'Tax', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (373, 'Audit', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (374, 'Wind control', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (375, 'Financial manager', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (376, 'CFO', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (377, 'Financial director', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (378, 'Financial Officer', 365);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (379, 'Legal', 342);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (380, 'Legal Specialist/Assistant', 379);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (381, 'Lawyer', 379);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (382, 'Legal advisor', 379);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (383, 'Legal Director', 379);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (384, 'Legal Manager', 379);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (385, 'Legal Director', 379);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (386, 'Other functional positions', 342);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (387, 'Other functional positions', 386);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (388, 'Sales', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (389, 'Sales', 388);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (390, 'Sales', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (391, 'Salesperson', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (392, 'Sales Manager', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (393, 'Customer Representative', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (394, 'Key account representative', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (395, 'BD manager', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (396, 'Business channel', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (397, 'Channel Sales', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (398, 'Agent sales', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (399, 'Sales Assistant', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (400, 'Tele-marketing', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (401, 'Sales consultant', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (402, 'Commodity manager', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (403, 'Advertising sales', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (404, 'Internet Marketing', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (405, 'Marketing Director', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (406, 'Sales Engineer', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (407, 'Account Manager', 389);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (408, 'Sales management', 388);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (409, 'Sales management', 408);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (410, 'Director of Sales', 408);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (411, 'Commercial Director', 408);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (412, 'Regional Director', 408);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (413, 'City ​​manager', 408);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (414, 'Sales VP', 408);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (415, 'Team manager', 408);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (416, 'Team manager', 388);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (417, 'Other sales positions', 416);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (418, 'Media', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (419, 'Editing/writing/publishing', 418);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (420, 'Editing/writing/publishing', 419);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (421, 'Reporter', 419);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (422, 'Edit', 419);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (423, 'Editing', 419);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (424, 'Contributor', 419);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (425, 'Publishing', 419);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (426, 'Proofreading', 419);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (427, 'Editor-in-Chief', 419);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (428, 'Self-media', 419);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (429, 'PR medium', 418);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (430, 'PR medium', 429);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (431, 'Media manager', 429);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (432, 'Media specialist', 429);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (433, 'Advertising coordination', 429);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (434, 'Brand PR', 429);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (435, 'Event planning and execution', 429);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (436, 'Media planning', 429);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (437, 'Conference and exhibition', 418);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (438, 'Conference and exhibition', 437);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (439, 'Conference Event Sales', 437);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (440, 'Conference event planning', 437);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (441, 'Execution of conference activities', 437);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (442, 'Exhibition event sales', 437);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (443, 'Exhibition event planning', 437);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (444, 'Execution of exhibition activities', 437);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (445, 'Advertise', 418);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (446, 'Advertise', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (447, 'Advertising creative', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (448, 'Art director', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (449, 'Advertising designer', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (450, 'Planning manager', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (451, 'Copywriting', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (452, 'Advertisement making', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (453, 'Media placement', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (454, 'Media cooperation', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (455, 'Media consultant', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (456, 'Ad review', 445);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (457, 'Film and television media', 418);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (458, 'Host/DJ', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (459, 'Anchor Assistant', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (460, 'Gaffer', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (461, 'Editor', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (462, 'Visual effect', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (463, 'Film and television media', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (464, 'Artist Assistant', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (465, 'Co-producer', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (466, 'Executive producer', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (467, 'Director/director', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (468, 'Photography', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (469, 'Video editing', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (470, 'Audio editing', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (471, 'Broker', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (472, 'Post-production', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (473, 'Video making', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (474, 'Film and television distribution', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (475, 'Television planning', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (476, 'Anchor', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (477, 'Actor/Voice/Model', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (478, 'Makeup/Styling/Clothing', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (479, 'Show management', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (480, 'Recording/Sound Effects', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (481, 'Producer', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (482, 'Screenwriter', 457);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (483, 'Other media jobs', 418);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (484, 'Other media jobs', 483);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (485, 'Finance', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (486, 'Investment and Financing', 485);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (487, 'Investment and Financing', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (488, 'Investment manager', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (489, 'Industry research', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (490, 'Asset Management', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (491, 'Chief Investment Officer', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (492, 'Invest in VP', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (493, 'Investment partner', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (494, 'Financing', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (495, 'M&A', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (496, 'Post-investment management', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (497, 'Investment assistant', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (498, 'Other investment and financing positions', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (499, 'Investment Advisor', 486);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (500, 'Wind control', 485);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (501, 'Wind control', 500);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (502, 'Lawyer', 500);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (503, 'Credit assessment', 500);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (504, 'Compliance audit', 500);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (505, 'Tax audit', 485);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (506, 'Audit', 505);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (507, 'Legal', 505);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (508, 'Accounting', 505);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (509, 'Liquidate', 505);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (510, 'Bank', 485);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (511, 'Bank', 510);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (512, 'Credit card sales', 510);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (513, 'Analyst', 510);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (514, 'Teller', 510);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (515, 'Business channel', 510);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (516, 'Lobby manager', 510);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (517, 'Financial advisor', 510);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (518, 'Account Manager', 510);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (519, 'Credit management', 510);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (520, 'Wind control', 510);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (521, 'Internet banking', 485);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (522, 'Internet banking', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (523, 'Financial product manager', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (524, 'Wind control', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (525, 'Collector', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (526, 'Analyst', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (527, 'Investment manager', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (528, 'Traders', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (529, 'Financial advisor', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (530, 'Compliance audit', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (531, 'Audit', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (532, 'Liquidate', 521);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (533, 'Insurance', 485);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (534, 'Insurance business', 533);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (535, 'Actuary', 533);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (536, 'Insurance claims', 533);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (537, 'Securities', 485);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (538, 'Securities', 537);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (539, 'Securities brokers', 537);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (540, 'Securities Analyst', 537);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (541, 'Other financial jobs', 485);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (542, 'Other financial jobs', 541);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (543, 'Education and training', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (544, 'Educational product development', 543);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (545, 'Educational product development', 544);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (546, 'Course Design', 544);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (547, 'Course editor', 544);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (548, 'Teacher', 544);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (549, 'Training research', 544);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (550, 'Trainer', 544);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (551, 'Training planning', 544);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (552, 'Other Educational Product Development Jobs', 544);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (553, 'Education administration', 543);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (554, 'Principal/Deputy Principal', 553);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (555, 'Education administration', 553);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (556, 'Principal/Deputy Principal', 553);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (557, 'Academic Administration', 553);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (558, 'Teaching management', 553);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (559, 'Headteacher/Counselor', 553);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (560, 'Teacher', 543);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (561, 'Japanese teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (562, 'Other foreign language teachers', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (563, 'Language teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (564, 'Math teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (565, 'Physics teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (566, 'Chemistry teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (567, 'Biology teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (568, 'Teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (569, 'Teaching assistant', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (570, 'High school teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (571, 'Junior high school teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (572, 'Primary School Teachers', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (573, 'Early childhood education', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (574, 'Science teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (575, 'Liberal Arts Teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (576, 'English teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (577, 'Music teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (578, 'Art teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (579, 'Physical education teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (580, 'Employment teacher', 560);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (581, 'IT training', 543);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (582, 'IT training', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (583, 'JAVA training instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (584, 'Android training instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (585, 'IOS training instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (586, 'PHP training instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (587, '.NET Training Instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (588, 'C++ training instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (589, 'Unity 3D Training Instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (590, 'Web front-end training instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (591, 'Software Testing Training Instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (592, 'Animation training instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (593, 'UI Design Training Instructor', 581);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (594, 'Vocational Training', 543);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (595, 'Accounting training instructor', 594);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (596, 'HR training instructor', 594);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (597, 'Trainer', 594);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (598, 'Expand training', 594);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (599, 'Admissions', 543);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (600, 'Course Consultant', 599);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (601, 'Admissions Counselor', 599);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (602, 'Study Abroad Consultant', 599);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (603, 'Specialty training', 543);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (604, 'Martial Arts Instructor', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (605, 'Roller skating coach', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (606, 'Acting teacher', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (607, 'Robot teacher', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (608, 'Calligraphy teacher', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (609, 'Piano teacher', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (610, 'Guitar teacher', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (611, 'Guzheng teacher', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (612, 'Coach', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (613, 'Dance instructor', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (614, 'Yoga instructor', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (615, 'Slimming consultant', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (616, 'Swimming coach', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (617, 'Fitness coach', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (618, 'Basketball/Badminton Coach', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (619, 'Taekwondo Instructor', 603);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (620, 'Other education and training positions', 543);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (621, 'Other education and training positions', 620);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (622, 'Medical health', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (623, 'Clinical Trials', 622);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (624, 'Clinical research', 623);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (625, 'Clinical coordination', 623);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (626, 'Clinical data analysis', 623);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (627, 'Medical director', 623);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (628, 'Doctor/Medical Technician', 622);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (629, 'Physician assistant', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (630, 'Medical Imaging', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (631, 'B ultrasound doctor', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (632, 'Traditional Chinese Medicine', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (633, 'Physician', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (634, 'Psychologist', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (635, 'Pharmacist', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (636, 'Dentist', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (637, 'Rehabilitation therapist', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (638, 'Optometrist', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (639, 'Radiologist', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (640, 'Laboratory physician', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (641, 'Other Doctor Jobs', 628);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (642, 'Nurse/Nursing', 622);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (643, 'Head nurse', 642);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (644, 'Nurse/Nursing', 642);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (645, 'Guide doctor', 642);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (646, 'Healthy plastic surgery', 622);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (647, 'Healthy plastic surgery', 646);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (648, 'Nutritionist', 646);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (649, 'Plastic surgeon', 646);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (650, 'Physical therapist', 646);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (651, 'Acupuncture and Massage', 646);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (652, 'Bio-pharmacy', 622);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (653, 'Bio-pharmacy', 652);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (654, 'Drug registration', 652);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (655, 'Pharmaceutical production', 652);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (656, 'Medical director', 652);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (657, 'Pharmaceutical research and development', 652);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (658, 'Medical instruments', 622);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (659, 'Medical Device Registration', 658);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (660, 'Medical Device Production/Quality Management', 658);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (661, 'Medical device research and development', 658);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (662, 'Pharmacy', 622);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (663, 'Pharmacy manager', 662);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (664, 'Licensed pharmacist/resident pharmacist', 662);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (665, 'Pharmacy clerk', 662);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (666, 'Medical Marketing/Media', 622);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (667, 'Medical Marketing/Media', 666);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (668, 'Medical Device Sales', 666);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (669, 'Medical editor', 666);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (670, 'Pharmacy editor', 666);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (671, 'Medical representative', 666);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (672, 'Health advisor', 666);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (673, 'Medical beauty consultation', 666);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (674, 'Other Healthcare Jobs', 622);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (675, 'Other Healthcare Jobs', 674);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (676, 'Sourcing/Trade', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (677, 'Purchase', 676);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (678, 'Purchase', 677);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (679, 'Purchasing Director', 677);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (680, 'Purchasing manager', 677);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (681, 'Purchasing Specialist', 677);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (682, 'Buyer', 677);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (683, 'Purchasing Engineer', 677);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (684, 'Purchasing Manager', 677);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (685, 'Purchasing Assistant', 677);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (686, 'Import and export trade', 676);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (687, 'Import and export trade', 686);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (688, 'Foreign trade manager', 686);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (689, 'Trade commissioner', 686);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (690, 'Foreign trade clerk', 686);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (691, 'Trade documentary', 686);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (692, 'Other Sourcing/Trade Positions', 676);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (693, 'Other Sourcing/Trade Jobs', 692);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (694, 'Supply Chain/Logistics', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (695, 'Logistics', 694);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (696, 'Logistics', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (697, 'Supply Chain Specialist', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (698, 'Supply chain manager', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (699, 'Logistic specialist', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (700, 'Logistics Manager', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (701, 'Logistics operation', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (702, 'Logistics Documentary', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (703, 'Trade documentary', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (704, 'Warehouse scheduling', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (705, 'Warehouse project', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (706, 'Transportation Manager/Supervisor', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (707, 'Freight forwarder', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (708, 'Freight Forwarding Manager', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (709, 'Water/Air/Ground Operations', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (710, 'Customs broker', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (711, 'Inspection staff', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (712, 'Verifier', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (713, 'Documentary', 695);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (714, 'Warehousing', 694);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (715, 'Warehousing', 714);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (716, 'Warehouse material manager', 714);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (717, 'Warehousing material specialist', 714);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (718, 'Warehousing material items', 714);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (719, 'Warehouse management', 714);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (720, 'Warehouse clerk', 714);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (721, 'Matching/arranging/picking/delivery', 714);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (722, 'Transportation', 694);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (723, 'Transportation', 722);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (724, 'Freight driver', 722);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (725, 'Container management', 722);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (726, 'Delivery', 722);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (727, 'Express delivery', 722);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (728, 'Senior Supply Chain Jobs', 694);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (729, 'Senior Supply Chain Jobs', 728);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (730, 'Supply Chain Director', 728);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (731, 'Logistics Director', 728);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (732, 'Other Supply Chain Jobs', 694);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (733, 'Other Supply Chain Jobs', 732);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (734, 'Real Estate/Construction', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (735, 'Real estate planning and development', 734);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (736, 'Real estate planning and development', 735);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (737, 'Real estate planning', 735);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (738, 'Real estate project management', 735);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (739, 'Real estate bidding', 735);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (740, 'Design and decoration and municipal construction', 734);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (741, 'Weak current engineer', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (742, 'Water supply and drainage engineer', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (743, 'HVAC engineer', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (744, 'Curtain Wall Engineer', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (745, 'Soft decoration designer', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (746, 'Construction Worker', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (747, 'Mapping/Surveying', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (748, 'Material clerk', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (749, 'BIM engineer', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (750, 'Decoration project manager', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (751, 'Design and decoration and municipal construction', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (752, 'Senior Construction Engineer', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (753, 'Construction engineer', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (754, 'Architectural Designer', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (755, 'Civil/Civil/Structural Engineer', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (756, 'Interior design', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (757, 'Garden design', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (758, 'Urban Planning and Design', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (759, 'Engineering supervision', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (760, 'Project costs', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (761, 'Pre-settlement', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (762, 'Engineering data management', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (763, 'Construction site management', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (764, 'Landscape Design', 740);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (765, 'Real estate brokerage', 734);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (766, 'Real estate brokerage', 765);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (767, 'Real estate consultant', 765);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (768, 'Real estate appraisal', 765);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (769, 'Real estate agency', 765);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (770, 'Property management', 734);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (771, 'Property maintenance', 770);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (772, 'Green chemical', 770);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (773, 'Property manager', 770);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (774, 'Property manager', 770);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (775, 'Property rental sales', 770);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (776, 'Property investment management', 770);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (777, 'Premium Real Estate Jobs', 734);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (778, 'Premium Real Estate Jobs', 777);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (779, 'Real estate project director', 777);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (780, 'Real estate planning director', 777);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (781, 'Real estate bidding director', 777);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (782, 'Property Director', 777);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (783, 'Real estate sales director', 777);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (784, 'Other real estate jobs', 734);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (785, 'Other real estate jobs', 784);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (786, 'Consulting / Translation / Legal', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (787, 'Consulting/Research', 786);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (788, 'Intellectual Property/Patent/Trademark Attorney', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (789, 'Psychological counseling', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (790, 'Marriage counseling', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (791, 'Consulting/Research', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (792, 'Business management consulting', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (793, 'Consulting Director', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (794, 'Data Analyst', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (795, 'Consulting Manager', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (796, 'Financial advisor', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (797, 'IT consultant', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (798, 'Human resources consultant', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (799, 'Consulting project management', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (800, 'Strategic Consulting', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (801, 'Headhunting consultant', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (802, 'Market research', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (803, 'Other consultants', 787);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (804, 'Lawyer', 786);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (805, 'Intellectual Property Lawyer', 804);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (806, 'Paralegal', 804);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (807, 'Patent attorney', 804);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (808, 'Firm lawyer', 804);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (809, 'Corporate legal affairs', 804);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (810, 'Translate', 786);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (811, 'English translation', 810);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (812, 'Japanese translation', 810);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (813, 'Korean/Korean translation', 810);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (814, 'French translation', 810);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (815, 'Simultaneous interpretation', 810);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (816, 'German translation', 810);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (817, 'Russian translation', 810);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (818, 'Spanish translation', 810);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (819, 'Translation into other languages', 810);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (820, 'Other consulting positions', 786);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (821, 'Other consulting/translation jobs', 820);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (822, 'Management trainee/reserve cadre', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (823, 'Tube culture', 822);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (824, 'Management Trainee', 823);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (825, 'Reserve cadres', 823);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (826, 'Other Management Trainee jobs', 822);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (827, 'Other internship/training/reserve positions', 826);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (828, 'Travel', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (829, 'Travel services', 828);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (830, 'Travel services', 829);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (831, 'Meter', 829);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (832, 'Visa', 829);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (833, 'Trip consultant', 829);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (834, 'Tourist guide', 829);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (835, 'Book a ticket', 829);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (836, 'Tourism product development/planning', 828);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (837, 'Tourism product development/planning', 836);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (838, 'Travel product manager', 836);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (839, 'Travel planner', 836);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (840, 'Other travel jobs', 828);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (841, 'Other travel jobs', 840);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (842, 'Service industry', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (843, 'Security/Housekeeping/Maintenance', 842);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (844, 'Security guard', 843);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (845, 'Cleaning', 843);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (846, 'Nanny', 843);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (847, 'Confinement', 843);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (848, 'Nursery teacher', 843);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (849, 'Care workers', 843);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (850, 'Security inspector', 843);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (851, 'Phone Repair', 843);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (852, 'Appliance Repair', 843);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (853, 'Security manager', 843);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (854, 'Pet service', 842);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (855, 'Pet grooming', 854);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (856, 'Veterinarian', 854);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (857, 'Wedding/Floral', 842);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (858, 'Florist', 857);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (859, 'Wedding planner', 857);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (860, 'Health & Beauty', 842);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (861, 'Makeup Consultant', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (862, 'Tattoo artist', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (863, 'Esthetician', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (864, 'Hairdressing Apprentice', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (865, 'Beauty shop manager', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (866, 'Foot therapist', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (867, 'Masseur', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (868, 'Hairstylist', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (869, 'Manicurist', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (870, 'Makeup artist', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (871, 'Hairdresser', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (872, 'Beautician/Consultant', 860);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (873, 'Hotel', 842);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (874, 'Etiquette welcome', 873);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (875, 'Front Office Manager', 873);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (876, 'Room manager', 873);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (877, 'Cashier', 873);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (878, 'Hotel reception', 873);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (879, 'Room Attendant', 873);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (880, 'Hotel manager', 873);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (881, 'FOOD', 842);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (882, 'Back kitchen', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (883, 'Side dish', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (884, 'Tea master', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (885, 'Pastry chef', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (886, 'Catering Apprentice', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (887, 'Baker', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (888, 'Executive Chef', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (889, 'Head chef', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (890, 'Waiter', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (891, 'Dishwasher', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (892, 'Cold dish chef', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (893, 'Chinese chef', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (894, 'Western chef', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (895, 'Japanese chef', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (896, 'BBQ master', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (897, 'FOOD', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (898, 'Cashier', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (899, 'Waiter', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (900, 'Chef', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (901, 'Coffee maker', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (902, 'Food delivery man', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (903, 'Restaurant manager', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (904, 'Foreman', 881);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (905, 'Retail', 842);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (906, 'Supervision / shop tour', 905);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (907, 'Exhibitor', 905);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (908, 'Tally clerk', 905);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (909, 'Loss preventer', 905);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (910, 'Store manager', 905);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (911, 'Cashier', 905);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (912, 'Shopping guide', 905);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (913, 'Shop assistant/salesperson', 905);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (914, 'Store manager', 905);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (915, 'Work out', 842);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (916, 'Membership Advisor', 915);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (917, 'Lifeguard', 915);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (918, 'Fitness', 915);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (919, 'Yoga instructor', 915);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (920, 'Slimming consultant', 915);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (921, 'Swimming coach', 915);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (922, 'Body Coach', 915);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (923, 'Dance instructor', 915);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (924, 'Fitness coach', 915);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (925, 'Other service jobs', 842);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (926, 'Other service jobs', 925);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (927, 'Manufacturing', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (928, 'Production operation', 927);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (929, 'Production operation', 928);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (930, 'Factory Manager / Factory Manager', 928);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (931, 'Production manager', 928);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (932, 'Production Manager / Workshop Director', 928);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (933, 'Production team leader/elongation', 928);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (934, 'Producer', 928);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (935, 'Production equipment management', 928);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (936, 'Production Planning/Material Control', 928);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (937, 'Production documentary', 928);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (938, 'Quality and Safety', 927);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (939, 'Quality inspector', 938);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (940, 'Quality Management/Testing', 938);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (941, 'Reliability engineer', 938);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (942, 'Failure analyst', 938);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (943, 'Certified Engineer', 938);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (944, 'System engineer', 938);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (945, 'Auditor', 938);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (946, 'Security officer', 938);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (947, 'Automotive Quality Engineer', 938);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (948, 'New energy', 927);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (949, 'Battery engineer', 948);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (950, 'Electrical engineer', 948);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (951, 'Harness Design', 948);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (952, 'Charging pile design', 948);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (953, 'Car manufacturer', 927);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (954, 'Automotive Design', 953);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (955, 'Body/Styling', 953);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (956, 'Chassis Engineer', 953);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (957, 'Power system engineer', 953);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (958, 'Automotive electronics engineer', 953);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (959, 'Auto Parts Design', 953);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (960, 'Automotive project management', 953);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (961, 'Interior and exterior design engineer', 953);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (962, 'Assembly engineer', 953);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (963, 'Auto Sales and Service', 927);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (964, 'Car sales', 963);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (965, 'Auto Parts Sales', 963);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (966, 'Auto Service Consultant', 963);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (967, 'Car repair', 963);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (968, 'Car beauty', 963);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (969, 'Car damage claims', 963);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (970, 'Used Car Appraiser', 963);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (971, '4S shop manager/maintenance station manager', 963);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (972, 'Car tuning engineer', 963);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (973, 'Mechanical design and manufacturing', 927);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (974, 'Mechanical design and manufacturing', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (975, 'Heat Conduction', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (976, 'Lean Engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (977, 'Mechanical Engineers', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (978, 'Mechanical designer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (979, 'Mechanical equipment engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (980, 'Mechanical Repair/Maintenance', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (981, 'Mechanical Drawing', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (982, 'Mechanical Structural Engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (983, 'Industrial engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (984, 'Process/Process Engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (985, 'Materials engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (986, 'Electrical and Mechanical Engineers', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (987, 'CNC/CNC', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (988, 'Stamping Engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (989, 'Fixture Engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (990, 'Mold engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (991, 'Welding Engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (992, 'Injection molding engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (993, 'Casting/Forging Engineer', 973);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (994, 'Chemical', 927);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (995, 'Chemical engineer', 994);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (996, 'Laboratory technician', 994);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (997, 'Chemical analysis', 994);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (998, 'Coating research and development', 994);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (999, 'Cosmetics research and development', 994);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1000, 'Food/Beverage R&D', 994);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1001, 'Apparel / Textile / leather', 927);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1002, 'Apparel/Textile Design', 1001);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1003, 'Development of fabric accessories', 1001);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1004, 'Proofing/plate making', 1001);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1005, 'Apparel/Textile/Leather Documentary', 1001);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1006, 'Mechanic/general worker', 927);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1007, 'Sewing', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1008, 'Porter', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1009, 'General worker/Operator', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1010, 'Forklift', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1011, 'Forklift', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1012, 'Welder', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1013, 'TIG welder', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1014, 'Electrician', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1015, 'Carpentry', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1016, 'Lacquer work', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1017, 'Turner', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1018, 'Grinder', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1019, 'Miller', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1020, 'Fitter', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1021, 'Driller', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1022, 'Riveter', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1023, 'Sheet metal', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1024, 'Polishing', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1025, 'Mechanic', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1026, 'Bender', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1027, 'Electroplater', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1028, 'Sprayer', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1029, 'Injection moulder', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1030, 'Assembler', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1031, 'Packing worker', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1032, 'Air conditioner', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1033, 'Elevator', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1034, 'Boilerman', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1035, 'Apprentice', 1006);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1036, 'Other Manufacturing Jobs', 927);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1037, 'Other Manufacturing Jobs', 1036);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1038, 'Other', 0);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1039, 'Other job categories', 1038);
INSERT INTO `wk_hrm_recruit_post_type` VALUES (1040, 'Other positions', 1039);

-- ----------------------------
-- Table structure for wk_hrm_salary_archives
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_archives`;
CREATE TABLE `wk_hrm_salary_archives`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `change_reason` int(0) NOT NULL COMMENT 'Reasons for salary adjustment 1 Approved for entry 2 Regularization 3 Promotion 4 Transfer 5 Mid-year salary adjustment 6 Annual salary adjustment 7 Special salary adjustment 8 Others',
  `change_data` date NOT NULL COMMENT 'Last adjustment date',
  `employee_id` int(0) NOT NULL COMMENT 'Employee id',
  `change_type` int(0) NULL DEFAULT 0 COMMENT '0 Undetermined salary 1 Salary fixed 2 Salary adjusted',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Remark',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `wk_hrm_salary_archives_change_reason_index`(`change_reason`) USING BTREE,
  INDEX `wk_hrm_salary_archives_employee_id_index`(`employee_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Salary file table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_archives
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_archives_option
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_archives_option`;
CREATE TABLE `wk_hrm_salary_archives_option`  (
  `employee_id` int(0) NOT NULL COMMENT 'Employee id',
  `is_pro` int(0) NOT NULL COMMENT 'Is it a trial period 0 official 1 trial period',
  `code` int(0) NOT NULL COMMENT 'Salary item code',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Salary item name',
  `value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Salary',
  INDEX `wk_hrm_salary_archives_option_employee_id_index`(`employee_id`) USING BTREE,
  INDEX `wk_hrm_salary_archives_option_is_pro_index`(`is_pro`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_archives_option
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_change_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_change_record`;
CREATE TABLE `wk_hrm_salary_change_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `employee_id` int(0) NOT NULL COMMENT 'Employee id',
  `record_type` int(0) NOT NULL DEFAULT 1 COMMENT 'Record Type 1 Fixed Salary 2 Salary Adjustment',
  `change_reason` int(0) NOT NULL COMMENT 'Reasons for salary adjustment 1 Approved for entry 2 Regularization 3 Promotion 4 Transfer 5 Mid-year salary adjustment 6 Annual salary adjustment 7 Special salary adjustment 8 Others',
  `enable_date` date NOT NULL COMMENT 'Effective time',
  `pro_before_sum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT 'Salary before probation period adjustment',
  `pro_after_sum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT 'Adjusted salary for probationary period',
  `pro_salary` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Probationary salary details',
  `before_sum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT 'Salary json before formal adjustment',
  `after_sum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT 'Officially Adjusted Salary',
  `salary` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Official salary details json',
  `status` int(0) NOT NULL DEFAULT 0 COMMENT 'Status 0 Not active 1 Active 2 Cancelled',
  `employee_status` int(0) NULL DEFAULT NULL,
  `before_total` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT 'Gross salary before adjustment',
  `after_total` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT 'Adjusted gross salary',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Remark',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT 'Creation time',
  `create_user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `wk_hrm_salary_change_record_employee_id_index`(`employee_id`) USING BTREE,
  INDEX `wk_hrm_salary_change_record_status_index`(`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Salary adjustment record sheet' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_change_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_change_template
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_change_template`;
CREATE TABLE `wk_hrm_salary_change_template`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Template name',
  `is_default` int(0) NOT NULL DEFAULT 0 COMMENT 'Default 0 no 1 yes',
  `value` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34635 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Salary adjustment template' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_change_template
-- ----------------------------
INSERT INTO `wk_hrm_salary_change_template` VALUES (1, 'Default template', 1, '[{\"code\":10101,\"name\":\"Basic salary\"},{\"code\":10102,\"name\":\"Post salary\"},{\"code\":10103,\"name\":\"Job salary\"}]', '2020-11-24 21:20:43', 0);

-- ----------------------------
-- Table structure for wk_hrm_salary_config
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_config`;
CREATE TABLE `wk_hrm_salary_config`  (
  `config_id` int(0) NOT NULL AUTO_INCREMENT,
  `salary_cycle_start_day` int(0) NULL DEFAULT NULL COMMENT 'Pay Period Start Date',
  `salary_cycle_end_day` int(0) NULL DEFAULT NULL COMMENT 'Pay Period End Date',
  `pay_type` int(0) NULL DEFAULT NULL COMMENT 'Pay Date Type 1 Current Month 2 Next Month',
  `pay_day` int(0) NULL DEFAULT NULL COMMENT 'Pay date',
  `social_security_month_type` int(0) NULL DEFAULT NULL COMMENT 'Corresponding social security natural month 0 previous month 1 current month 2 next month',
  `salary_start_month` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Salary start month (Example 2020.05)',
  `social_security_start_month` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Social Security Start Month (Example 2020.05)',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Salary initial configuration' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_config
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_group
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_group`;
CREATE TABLE `wk_hrm_salary_group`  (
  `group_id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'Pay group id',
  `group_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Pay Group Name',
  `dept_ids` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Department scope',
  `employee_ids` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Employee Scope',
  `salary_standard` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '21.75' COMMENT 'Monthly salary',
  `change_rule` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'Calculated based on the mixed salary before and after the regularization/salary adjustment' COMMENT 'Regularization and salary adjustment monthly rules',
  `rule_id` int(0) NULL DEFAULT NULL COMMENT 'Tax rule id',
  `create_time` datetime(0) NOT NULL,
  `create_user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Pay group' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_group
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_month_emp_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_month_emp_record`;
CREATE TABLE `wk_hrm_salary_month_emp_record`  (
  `s_emp_record_id` int(0) NOT NULL AUTO_INCREMENT,
  `s_record_id` int(0) NULL DEFAULT NULL COMMENT 'Generate salary id monthly',
  `employee_id` int(0) NULL DEFAULT NULL COMMENT 'Employee id',
  `actual_work_day` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Actual Payable Hours',
  `need_work_day` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Monthly pay hours',
  `year` int(0) NULL DEFAULT NULL COMMENT 'Year',
  `month` int(0) NULL DEFAULT NULL COMMENT 'Moon',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`s_emp_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Employee monthly salary records' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_month_emp_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_month_option_value
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_month_option_value`;
CREATE TABLE `wk_hrm_salary_month_option_value`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `s_emp_record_id` int(0) NULL DEFAULT NULL COMMENT 'Monthly employee salary record id',
  `code` int(0) NULL DEFAULT NULL,
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 103 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Monthly Employee Salary Item Table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_month_option_value
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_month_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_month_record`;
CREATE TABLE `wk_hrm_salary_month_record`  (
  `s_record_id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Report title',
  `year` int(0) NULL DEFAULT NULL COMMENT 'Year',
  `month` int(0) NULL DEFAULT NULL COMMENT 'Moon',
  `num` int(0) NULL DEFAULT NULL COMMENT 'Payroll',
  `start_time` date NULL DEFAULT NULL COMMENT 'Payroll start date',
  `end_time` date NULL DEFAULT NULL COMMENT 'Payable end date',
  `personal_insurance_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Personal social security amount',
  `personal_provident_fund_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Personal Provident Fund Amount',
  `corporate_insurance_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Company social security amount',
  `corporate_provident_fund_amount` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Company social security amount',
  `expected_pay_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Advance salary',
  `personal_tax` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Personal Income Tax',
  `real_pay_salary` decimal(10, 2) NULL DEFAULT NULL COMMENT 'Estimated actual salary',
  `option_head` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'Payroll Header',
  `examine_record_id` int(0) NULL DEFAULT NULL COMMENT 'Audit record id',
  `check_status` int(0) NULL DEFAULT 5 COMMENT 'Status 0 to be reviewed, 1 to pass, 2 to be rejected, 3 to be reviewed 4: withdrawn 5 not submitted 10 historical salary 11 Accounting completed',
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`s_record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Monthly salary records' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_month_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_option
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_option`;
CREATE TABLE `wk_hrm_salary_option`  (
  `option_id` int(0) NOT NULL AUTO_INCREMENT,
  `code` int(0) NULL DEFAULT NULL,
  `parent_code` int(0) NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Name',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `is_fixed` int(0) NULL DEFAULT NULL COMMENT 'Fixed 0 No 1 Yes',
  `is_plus` int(0) NULL DEFAULT NULL COMMENT 'Whether to add the item 0 minus 1 plus',
  `is_tax` int(0) NULL DEFAULT NULL COMMENT 'Taxable or not 0 No 1 Yes',
  `is_show` int(0) NULL DEFAULT NULL COMMENT 'Show or not 0 No 1 Yes',
  `is_compute` int(0) NULL DEFAULT NULL COMMENT 'Whether to participate in salary calculation 0 No 1 Yes',
  `is_open` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`option_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1269 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'System salary' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_option
-- ----------------------------
INSERT INTO `wk_hrm_salary_option` VALUES (1161, 10, 0, 'Basic wage', NULL, 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1165, 20, 0, 'Allowance', 'Benefits provided by the company to employees', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1170, 30, 0, 'Variable salary', 'Variable wages need to be entered manually before the monthly salary calculation', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1172, 40, 0, 'Bonus', NULL, 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1176, 50, 0, 'Commission salary', NULL, 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1178, 60, 0, 'Piece rate', NULL, 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1180, 70, 0, 'Hourly wage', NULL, 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1182, 80, 0, 'Seniority / seniority salary', NULL, 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1185, 90, 0, 'Title salary', NULL, 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1189, 100, 0, 'Withholding and payment', 'Fees paid by the company on behalf of individuals, such as personal social security and personal provident fund. Salary items under this category will be deducted from the taxable income, affecting the calculation of personal tax', 0, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1192, 110, 0, 'Corporate social security', 'Salary items under this category do not participate in wage calculation, but only in enterprise cost statistics or social security cost analysis', 0, 1, 0, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1194, 120, 0, 'Enterprise Provident Fund', 'Salary items under this category do not participate in wage calculation, but only in enterprise cost statistics or social security cost analysis', 0, 1, 0, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1196, 130, 0, 'Reissue before tax', 'Pre-tax reissue, paid together with the salary of the month, need to participate in tax calculation', 0, 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1198, 140, 0, 'Pre-tax deduction', 'The amount deducted from the salary for the month before tax will affect the total taxable salary for the month', 0, 0, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1200, 150, 0, 'Reissue after tax', 'After-tax reissue, do not participate in the calculation of the salary of the month, it will affect the actual income of the month', 0, 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1202, 160, 0, 'After-tax deduction', 'The amount deducted from the after-tax salary does not participate in the calculation of the salary for the month', 0, 0, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1206, 170, 0, 'Special tax', 'Subjects in other fields are not involved in salary calculation, but tax calculation is required', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1208, 180, 0, 'Overtime salary', 'The company\'s overtime compensation calculated by the employee\'s overtime hours', 1, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1210, 190, 0, 'Attendance deduction details', NULL, 1, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1217, 200, 0, 'Total Attendance Deduction', NULL, 1, 0, 1, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1219, 210, 0, 'Salary payable', NULL, 1, 2, 2, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1221, 220, 0, 'Taxable salary', NULL, 1, 2, 2, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1223, 230, 0, 'Personal Income Tax', NULL, 1, 2, 2, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1225, 240, 0, 'Payroll', NULL, 1, 2, 2, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1227, 10101, 10, 'Basic wage', 'The standard salary agreed in the labor contract.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1228, 10102, 10, 'Wage jobs', 'According to the position, responsibilities, skill requirements, etc., different positions have different wages.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1229, 10103, 10, 'Job salary', 'According to different factors such as the level of the position, the level of business technology, etc., the salary is different and changes with the change of position.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1230, 20101, 20, 'Housing allowance', 'Subsidies for employees to solve housing problems.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1231, 20102, 20, 'High temperature allowance', 'Employers arrange for workers to work in high temperature weather. When the temperature is higher than 33°C, high temperature subsidies should be paid.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1232, 20103, 20, 'Transportation allowance', 'Transportation subsidies paid by enterprises on a monthly basis', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1233, 20104, 20, 'Meal supplement', 'Enterprises do not uniformly provide meals, but a standard monthly lunch or dinner subsidy', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1234, 30101, 30, 'Performance pay', 'Based on the actual labor results or performance of the employees as the evaluation standard, a certain reward salary will be paid.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1235, 40101, 40, 'Quarterly award', 'For outstanding employees, a certain reward salary will be given on a quarterly basis.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1236, 40102, 40, 'Perfect Attendance Award', 'For employees who arrive at work according to the company\'s regulations and do not appear late or leave early, the assessment period is generally monthly.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1237, 40103, 40, 'Referral Award', 'In the process of recruiting talents, the company will give incentive wages to internal employees who recommend talents and successfully join the company.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1238, 50101, 50, 'Sales commission', 'For sales or business personnel, profits are usually divided between the enterprise and its employees according to a certain percentage.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1239, 60101, 60, 'Piece rate', 'Wages calculated and paid according to the amount of labor performed by the employees and the unit price specified in advance.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1240, 70101, 70, 'Hourly wage', 'Wages calculated and paid according to the employee\'s working hours, according to the wage standard and grade', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1241, 80101, 80, 'Seniority salary', 'The economic compensation given by the enterprise is based on the number of working years of the employee, that is, the accumulation of the employee\'s work experience and labor contribution.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1242, 80102, 80, 'Age salary', 'Ageing wages are calculated according to the length of time an employee has worked in the company.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1243, 90101, 90, 'Title salary', 'The level of salary is linked to the title, the higher the title, the higher the salary.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1244, 90102, 90, 'Skill salary', 'Salaries are paid on the basis of the knowledge, skills and abilities that employees have acquired.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1245, 90103, 90, 'Academic salary', 'Appropriate allowances are given to employees based on their academic qualifications at educational institutions.', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1246, 100101, 100, 'Personal social security', 'The portion of social security paid by individuals', 0, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1247, 100102, 100, 'Personal provident fund', 'The portion of the provident fund that is paid by individuals', 0, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1248, 110101, 110, 'Corporate social security', 'Social security expenses borne by the company', 0, 1, 0, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1249, 120101, 120, 'Enterprise Provident Fund', 'Provident fund expenses borne by the company', 0, 1, 0, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1250, 130101, 130, 'Reissue before tax', 'If there is a missed payment in the previous month, the salary repaid this month', 0, 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1251, 140101, 140, 'Pre-tax deduction', 'If the deduction was not made in the previous month, the deduction will be made up in this month;', 0, 0, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1252, 150101, 150, 'Reissue after tax', 'Other after-tax reissue', 0, 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1253, 160101, 160, 'Party membership dues', 'The funds paid to the party organization for the party\'s cause and party\'s activities shall be collected by the company on its behalf.', 0, 0, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1254, 160102, 160, 'Union fee', 'Expenses paid by employees and required by the trade union to carry out various activities', 0, 0, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1255, 160103, 160, 'Complementary medicine', 'On the basis of basic medical insurance, enterprises pay additional supplementary medical insurance for employees.', 0, 0, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1256, 170101, 170, 'Commercial insurance', 'Commercial insurance money is not involved in the calculation, but it is taxed', 0, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1257, 180101, 180, 'Overtime salary', 'According to a certain percentage, the overtime pay is calculated according to the overtime hours.', 1, 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1258, 190101, 190, 'Late charge', 'If an employee is late for no reason, a certain salary will be deducted as punishment', 1, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1259, 190102, 190, 'Early withdrawal deduction', 'If an employee leaves early without any reason, a certain salary must be deducted as punishment', 1, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1260, 190103, 190, 'Deductions for absenteeism', 'Employees who are absent from work without reason will need to deduct a certain amount of wages as punishment', 1, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1261, 190104, 190, 'Holiday deduction', 'If the employee fails to show up on the working day due to personal leave, sick leave, etc., a certain salary must be deducted', 1, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1262, 190105, 190, 'Missing card deduction', 'If an employee lacks a card for no reason, a certain amount must be deducted as punishment', 1, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1263, 190106, 190, 'Comprehensive deduction', 'The amount of attendance deduction calculated based on the cumulative length or number of late arrivals and early departures per month', 1, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1264, 200101, 200, 'Total Attendance Deduction', 'Total deductions for 6 abnormal attendance including late arrival, early departure, absenteeism, lack of card, and request for leave', 1, 0, 1, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1265, 210101, 210, 'Salary payable', 'Salary payable = total employee salary - leave deduction - attendance deduction, etc.', 1, 2, 2, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1266, 220101, 220, 'Taxable salary', 'Taxable salary = payable salary - personal social security - personal provident fund - monthly deduction', 1, 2, 2, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1267, 230101, 230, 'Personal Income Tax', 'According to the IIT calculation rules, the calculated monthly IIT should be paid', 1, 2, 2, 1, 0, 1);
INSERT INTO `wk_hrm_salary_option` VALUES (1268, 240101, 240, 'Payroll', 'The actual monthly income earned by the employee.', 1, 2, 2, 1, 0, 1);

-- ----------------------------
-- Table structure for wk_hrm_salary_option_template
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_option_template`;
CREATE TABLE `wk_hrm_salary_option_template`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `code` int(0) NULL DEFAULT NULL,
  `parent_code` int(0) NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Name',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Remark',
  `is_fixed` int(0) NULL DEFAULT NULL COMMENT 'Fixed 0 No 1 Yes',
  `is_plus` int(0) NULL DEFAULT NULL COMMENT 'Whether to add the item 0 minus 1 plus 2 to calculate',
  `is_tax` int(0) NULL DEFAULT NULL COMMENT 'Taxable or not 0 No 1 Yes',
  `is_show` int(0) NULL DEFAULT NULL COMMENT 'Show or not 0 No 1 Yes',
  `is_compute` int(0) NULL DEFAULT NULL COMMENT 'Whether to participate in salary calculation 0 No 1 Yes',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'System Salary Item Template' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_option_template
-- ----------------------------
INSERT INTO `wk_hrm_salary_option_template` VALUES (1, 10, 0, 'Basic wage', NULL, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (2, 10101, 10, 'Basic wage', 'The standard salary agreed in the labor contract.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (3, 10102, 10, 'Wage jobs', 'According to the position, responsibilities, skill requirements, etc., different positions have different wages.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (4, 10103, 10, 'Job salary', 'According to different factors such as the level of the position, the level of business technology, etc., the salary is different and changes with the change of position.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (5, 20, 0, 'Allowance', 'Benefits provided by the company to employees', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (6, 20101, 20, 'Housing allowance', 'Subsidies for employees to solve housing problems.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (7, 20102, 20, 'High temperature allowance', 'Employers arrange for workers to work in high temperature weather. When the temperature is higher than 33°C, high temperature subsidies should be paid.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (8, 20103, 20, 'Transportation allowance', 'Transportation subsidies paid by enterprises on a monthly basis', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (9, 20104, 20, 'Meal supplement', 'Enterprises do not uniformly provide meals, but a standard monthly lunch or dinner subsidy', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (10, 30, 0, 'Variable salary', 'Variable wages need to be entered manually before the monthly salary calculation', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (11, 30101, 30, 'Performance pay', 'Based on the actual labor results or performance of the employees as the evaluation standard, a certain reward salary will be paid.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (12, 40, 0, 'Bonus', NULL, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (13, 40101, 40, 'Quarterly award', 'For outstanding employees, a certain reward salary will be given on a quarterly basis.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (14, 40102, 40, 'Perfect Attendance Award', 'For employees who arrive at work according to the company\'s regulations and do not appear late or leave early, the assessment period is generally monthly.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (15, 40103, 40, 'Referral Award', 'In the process of recruiting talents, the company will give incentive wages to internal employees who recommend talents and successfully join the company.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (16, 50, 0, 'Commission salary', NULL, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (17, 50101, 50, 'Sales commission', 'For sales or business personnel, profits are usually divided between the enterprise and its employees according to a certain percentage.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (18, 60, 0, 'Piece rate', NULL, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (19, 60101, 60, 'Piece rate', 'Wages calculated and paid according to the amount of labor performed by the employees and the unit price specified in advance.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (20, 70, 0, 'Hourly wage', NULL, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (21, 70101, 70, 'Hourly wage', 'Wages calculated and paid according to the employee\'s working hours, according to the wage standard and grade', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (22, 80, 0, 'Seniority / seniority salary', NULL, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (23, 80101, 80, 'Seniority salary', 'The economic compensation given by the enterprise is based on the number of working years of the employee, that is, the accumulation of the employee\'s work experience and labor contribution.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (24, 80102, 80, 'Age salary', 'Ageing wages are calculated according to the length of time an employee has worked in the company.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (25, 90, 0, 'Title salary', NULL, 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (26, 90101, 90, 'Title salary', 'The level of salary is linked to the title, the higher the title, the higher the salary.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (27, 90102, 90, 'Skill salary', 'Salaries are paid on the basis of the knowledge, skills and abilities that employees have acquired.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (28, 90103, 90, 'Academic salary', 'Appropriate allowances are given to employees based on their academic qualifications at educational institutions.', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (29, 100, 0, 'Withholding and payment', 'Fees paid by the company on behalf of individuals, such as personal social security and personal provident fund. Salary items under this category will be deducted from the taxable income, affecting the calculation of personal tax', 0, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (30, 100101, 100, 'Personal social security', 'The portion of social security paid by individuals', 0, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (31, 100102, 100, 'Personal provident fund', 'The portion of the provident fund that is paid by individuals', 0, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (32, 110, 0, 'Corporate social security', 'Salary items under this category do not participate in wage calculation, but only in enterprise cost statistics or social security cost analysis', 0, 1, 0, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (33, 110101, 110, 'Corporate social security', 'Social security expenses borne by the company', 0, 1, 0, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (34, 120, 0, 'Enterprise Provident Fund', 'Salary items under this category do not participate in wage calculation, but only in enterprise cost statistics or social security cost analysis', 0, 1, 0, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (35, 120101, 120, 'Enterprise Provident Fund', 'Provident fund expenses borne by the company', 0, 1, 0, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (36, 130, 0, 'Reissue before tax', 'Pre-tax reissue, paid together with the salary of the month, need to participate in tax calculation', 0, 1, 0, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (37, 130101, 130, 'Reissue before tax', 'If there is a missed payment in the previous month, the salary repaid this month', 0, 1, 0, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (38, 140, 0, 'Pre-tax deduction', 'The amount deducted from the salary for the month before tax will affect the total taxable salary for the month', 0, 0, 0, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (39, 140101, 140, 'Pre-tax deduction', 'If the deduction was not made in the previous month, the deduction will be made up in this month;', 0, 0, 0, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (40, 150, 0, 'Reissue after tax', 'After-tax reissue, do not participate in the calculation of the salary of the month, it will affect the actual income of the month', 0, 1, 0, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (41, 150101, 150, 'Reissue after tax', 'Other after-tax reissue', 0, 1, 0, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (42, 160, 0, 'After-tax deduction', 'The amount deducted from the after-tax salary does not participate in the calculation of the salary for the month', 0, 0, 0, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (43, 160101, 160, 'Party membership dues', 'The funds paid to the party organization for the party\'s cause and party\'s activities shall be collected by the company on its behalf.', 0, 0, 0, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (44, 160102, 160, 'Union fee', 'Expenses paid by employees and required by the trade union to carry out various activities', 0, 0, 0, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (45, 160103, 160, 'Complementary medicine', 'On the basis of basic medical insurance, enterprises pay additional supplementary medical insurance for employees.', 0, 0, 0, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (46, 170, 0, 'Special tax', 'Subjects in other fields are not involved in salary calculation, but tax calculation is required', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (47, 170101, 170, 'Commercial insurance', 'Commercial insurance money is not involved in the calculation, but it is taxed', 0, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (48, 180, 0, 'Overtime salary', 'The company\'s overtime compensation calculated by the employee\'s overtime hours', 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (49, 180101, 180, 'Overtime salary', 'According to a certain percentage, the overtime pay is calculated according to the overtime hours.', 1, 1, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (50, 190, 0, 'Attendance deduction details', NULL, 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (51, 190101, 190, 'Late charge', 'If an employee is late for no reason, a certain salary will be deducted as punishment', 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (52, 190102, 190, 'Early withdrawal deduction', 'If an employee leaves early without any reason, a certain salary must be deducted as punishment', 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (53, 190103, 190, 'Deductions for absenteeism', 'Employees who are absent from work without reason will need to deduct a certain amount of wages as punishment', 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (54, 190104, 190, 'Holiday deduction', 'If the employee fails to show up on the working day due to personal leave, sick leave, etc., a certain salary must be deducted', 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (55, 190105, 190, 'Missing card deduction', 'If an employee lacks a card for no reason, a certain amount must be deducted as punishment', 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (56, 190106, 190, 'Comprehensive deduction', 'The amount of attendance deduction calculated based on the cumulative length or number of late arrivals and early departures per month', 1, 0, 1, 1, 1);
INSERT INTO `wk_hrm_salary_option_template` VALUES (57, 200, 0, 'Total Attendance Deduction', NULL, 1, 0, 1, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (58, 200101, 200, 'Total Attendance Deduction', 'Total deductions for 6 abnormal attendance including late arrival, early departure, absenteeism, lack of card, and request for leave', 1, 0, 1, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (59, 210, 0, 'Salary payable', NULL, 1, 2, NULL, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (60, 210101, 210, 'Salary payable', 'Salary payable = total employee salary - leave deduction - attendance deduction, etc.', 1, 2, NULL, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (61, 220, 0, 'Taxable salary', NULL, 1, 2, NULL, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (62, 220101, 220, 'Taxable salary', 'Taxable salary = payable salary - personal social security - personal provident fund - monthly deduction', 1, 2, NULL, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (63, 230, 0, 'Personal Income Tax', NULL, 1, 2, NULL, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (64, 230101, 230, 'Personal Income Tax', 'According to the IIT calculation rules, the calculated monthly IIT should be paid', 1, 2, NULL, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (65, 240, 0, 'Payroll', NULL, 1, 2, NULL, 1, 0);
INSERT INTO `wk_hrm_salary_option_template` VALUES (66, 240101, 240, 'Payroll', 'The actual monthly income earned by the employee.', 1, 2, NULL, 1, 0);

-- ----------------------------
-- Table structure for wk_hrm_salary_slip
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_slip`;
CREATE TABLE `wk_hrm_salary_slip`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `record_id` int(0) NOT NULL COMMENT 'Payroll record id',
  `s_emp_record_id` int(0) NOT NULL,
  `employee_id` int(0) NOT NULL COMMENT 'Employee id',
  `year` int(0) NULL DEFAULT NULL,
  `month` int(0) NULL DEFAULT NULL,
  `read_status` int(0) NOT NULL DEFAULT 0 COMMENT 'View Status 0 Unread 1 Read',
  `real_salary` decimal(10, 2) NOT NULL COMMENT 'Payroll',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Remark',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT 'Release time',
  `create_user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `wk_hrm_salary_slip_read_status_index`(`read_status`) USING BTREE,
  INDEX `wk_hrm_salary_slip_record_id_index`(`record_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Pay slip' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_slip
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_slip_option
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_slip_option`;
CREATE TABLE `wk_hrm_salary_slip_option`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `slip_id` int(0) NOT NULL COMMENT 'Payroll id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Salary item name',
  `type` int(0) NOT NULL COMMENT 'Option Type 1 Category 2 Salary Component',
  `code` int(0) NOT NULL DEFAULT 0 COMMENT 'Salary item code',
  `value` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Salary item value',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Remark',
  `pid` int(0) NOT NULL DEFAULT 0 COMMENT 'Parent category id',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT 'Sort',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `wk_hrm_salary_slip_option_slip_id_index`(`slip_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 543 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Payroll payroll' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_slip_option
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_slip_record
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_slip_record`;
CREATE TABLE `wk_hrm_salary_slip_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `s_record_id` int(0) NOT NULL COMMENT 'Monthly salary record id',
  `salary_num` int(0) NOT NULL COMMENT 'Payroll total',
  `pay_num` int(0) NOT NULL COMMENT 'Number of people issued',
  `year` int(0) NOT NULL,
  `month` int(0) NOT NULL,
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `wk_hrm_salary_slip_record_year_month_index`(`year`, `month`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Payroll records' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_slip_record
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_slip_template
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_slip_template`;
CREATE TABLE `wk_hrm_salary_slip_template`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `template_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Template name',
  `hide_empty` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to hide empty salary items 0 not hidden 1 hidden',
  `default_option` int(0) NOT NULL DEFAULT 0 COMMENT 'Is it the default template item 0 no 1 yes',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34629 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Payroll Template' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_slip_template
-- ----------------------------
INSERT INTO `wk_hrm_salary_slip_template` VALUES (1, 'Default template', 0, 1, '2020-11-24 21:20:42', 0);

-- ----------------------------
-- Table structure for wk_hrm_salary_slip_template_option
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_slip_template_option`;
CREATE TABLE `wk_hrm_salary_slip_template_option`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `template_id` int(0) NOT NULL COMMENT 'Template id',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Salary item name',
  `type` int(0) NOT NULL COMMENT 'Option Type 1 Category 2 Salary Component',
  `code` int(0) NOT NULL DEFAULT 0 COMMENT 'Salary item code',
  `remark` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Remark',
  `pid` int(0) NOT NULL DEFAULT 0 COMMENT 'Parent category id',
  `is_hide` int(0) NOT NULL DEFAULT 0 COMMENT 'Whether to hide 0 no 1 yes',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT 'Sort',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `create_user_id` bigint(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Pay Slip Template Items' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_slip_template_option
-- ----------------------------

-- ----------------------------
-- Table structure for wk_hrm_salary_tax_rule
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_salary_tax_rule`;
CREATE TABLE `wk_hrm_salary_tax_rule`  (
  `rule_id` int(0) NOT NULL AUTO_INCREMENT,
  `rule_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Rule name',
  `tax_type` int(0) NULL DEFAULT NULL COMMENT 'Individual tax type 1 Income tax on wages and salaries 2 Income tax on labor remuneration 3 No tax',
  `is_tax` int(0) NULL DEFAULT NULL COMMENT 'Taxable or not 0 No 1 Yes',
  `marking_point` int(0) NULL DEFAULT NULL COMMENT 'Threshold point',
  `decimal_point` int(0) NULL DEFAULT NULL COMMENT 'Personal tax result (retain the decimal point)',
  `cycle_type` int(0) NULL DEFAULT NULL COMMENT 'Type of tax calculation cycle 1 December of the previous year to November of this year (the corresponding salary payment method is the payment of the salary of the previous month) 2 January to December this year (the corresponding salary payment method is the payment of the salary of the current month)',
  PRIMARY KEY (`rule_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Tax rules' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_salary_tax_rule
-- ----------------------------
INSERT INTO `wk_hrm_salary_tax_rule` VALUES (49, 'Salary income tax', 1, 1, 5000, 2, 1);
INSERT INTO `wk_hrm_salary_tax_rule` VALUES (50, 'Labor remuneration income tax', 2, 1, 800, 2, NULL);
INSERT INTO `wk_hrm_salary_tax_rule` VALUES (51, 'Tax free', 3, 0, 0, NULL, NULL);

-- ----------------------------
-- Table structure for wk_hrm_user_attendance
-- ----------------------------
DROP TABLE IF EXISTS `wk_hrm_user_attendance`;
CREATE TABLE `wk_hrm_user_attendance`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL COMMENT 'Userid',
  `attendance_id` int(0) NOT NULL COMMENT 'Attendance group id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'User and attendance group association table' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of wk_hrm_user_attendance
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
