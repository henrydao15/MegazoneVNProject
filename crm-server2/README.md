# Kuberix Customer Relationship Management (CRM) System v1.0

## CRM directory structure

``` lua
crm
├── admin         -- System Management Module and User Management Module
├── authorization -- Authentication module, currently only used for login authentication, may be changed in the future
├── bi            -- Business Intelligence Module
├── core          -- Common Code and Utilities
├── crm           -- Customer Management Module
├── examine       -- Approval module
├── gateway       -- Gateway module
├── job           -- Job/Schedule module
├── oa            -- OA module
└── work          -- Project Management Module
└── hrm           -- Human Resource Management Module

```
### Brief description

#### CRM/OA function:<br/>

Customer management: customer data entry, support data duplication check, data transfer<br/>
Lead Management: Support leads to convert customers<br/>
Customer pool management: multiple customer pools can be configured, and recovery rules can be set according to various conditions such as transactions<br/>
Business opportunity management: support setting multiple business opportunities, custom configuration of business opportunity stages<br/>
Contract Management: Contract Approval Flow Configuration<br/>
Payment management: payment collection review, approval flow configuration<br/>
Office approval: support configuration approval flow (custom configuration such as leave, reimbursement, loan, business trip, etc.)<br/>
Schedule/task: support task assignment, task<br/>

#### HR function:<br/>

Recruitment management: support candidate addition, screening, interview management<br/>
Personnel management: multi-dimensional organizational structure configuration<br/>
Salary management: flexible configuration of salary types<br/>
Social security management: support social security allocation in different regions of the country<br/>
Performance appraisal: flexibly configure the performance appraisal process, support OKR and KPI performance appraisal template<br/><br/>

#### Staff side:<br/>

Employee self-service inquiry of personal information, salary information, social security information, performance appraisal information<br/><br/>

#### System Configuration:<br/>

System custom field configuration<br/>
Approval Flow Configuration<br/>
Employee organizational structure configuration<br/>
Role permission configuration (accurate to field permission)<br/>
Log Configuration<br/>
Rules of the High Seas Configuration<br/>
Business parameter configuration<br/>
Initialize data configuration<br/>
Other configuration<br/>



## The main technology stack

| Name                 | Version                   | Description                                       |
|----------------------|---------------------------|---------------------------------------------------|
| spring-cloud-alibaba | 2.2.1.RELEASE(Hoxton.SR3) | Core framework                                    |
| swagger              | 2.9.2                     | Interface documentation                           |
| mybatis-plus         | 3.3.0                     | ORM framework                                     |
| sentinel             | 2.2.1.RELEASE             | Circuit breaker and current limiting              |
| nacos                | 1.2.1.RELEASE             | Registry and distributed configuration management |
| seata                | 1.2.0                     | Distributed transaction                           |
| elasticsearch        | 2.2.5.RELEASE(6.8.6)      | Search engine middleware                          |
| jetcache             | 2.6.0                     | Distributed cache framework                       |
| xxl-job              | 2.1.2                     | Distributed Timing Task Framework                 |
| gateway              | 2.2.2.RELEASE             | Microservice Gateway                              |
| feign                | 2.2.2.RELEASE             | Service call                                      |


## CRM architecture diagram


<img src="https://images.gitee.com/uploads/images/2020/0910/094237_e7cb3bca_1096736.jpeg" width="650">

## CRM Introduction

### Pre-environment
- Jdk1.8
- Maven3.5.0+
- Mysql5.7.20
- Redis
- Elasticsearch 6.8.6
- Seata（1.2.0）
- Sentinel（1.7.2）
- Nacos（1.2.1)

### Install

### One-click Installation

This project supports Docker one-click installation (it is recommended to configure 4-core 16G or more)

### Manual Installation

#### 1. Import and initialize sql, the independent database used by the gateway module under the current project, and the same database used by other modules


- Install nacos and create a new database `nacos` Run `DB/nacos.sql` in the `nacos` database<br/>
  Modify the nacos installation directory /conf/application.properties file, modify the data persistence type to mysql, add the url, user name and password of the mysql data source, and configure as follows. <br/>

```
   spring.datasource.platform=mysql
   db.num=1
   db.url.0=jdbc:mysql://127.0.0.1:3306/nacos?characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true
   db.user=root
   db.password=password
```

- Modify the gateway module database configuration to `nacos` database
- Initialize the rest of the module database: Create a new database `wk_crm_single` Run `DB/wk_crm_single.sql` in the `wk_crm_single` database
- Initialize the scheduled task module database: create a new database `xxl_job` and run `DB/xxl_job.sql` in the `xxl_job` database
- Initialize the seata database: create a new database `seata` and run `DB/seata.sql` in the `seata` database


#### 2. Execute in the project root directory `mvn install`



#### 3. Configure database account information and redis account information in the resource directory under each module`(The application-dev.yml configuration file is used by default, and the application-test.yml configuration file is used by default by the startup script after packaging)`



#### 4. Modify the elasticsearch configuration in `crm\src\main\resources\application-dev.yml`


```
spring.elasticsearch.rest.uris = Elasticsearch address (Example: 127.0.0.1:9200)
spring.elasticsearch.rest.username = Elasticsearch username (Example: Elastic has no password and can be left blank)
spring.elasticsearch.rest.password = Elasticsearch password (Example: password No password can be left blank)

```

#### 5. (Optional) Modify the file upload address in the system. The default is local configuration. For local upload, you need to configure the public network address to point to the server gateway.

```
crm.upload.config:1                File upload configuration 1: Local 2: Alibaba Cloud OSS
crm.upload.oss                     The configuration content required for uploading files by oss
crm.upload.oss.bucketName          Two buckets need to be configured, 0 means that the file upload address can be accessed only after login, and 1 means that the file upload address is completely public
crm.upload.local                   Configuration content required for uploading files locally
crm.upload.local.uploadPath        Need to configure two addresses 0 to log in to access the file upload address, 1 to fully disclose the file upload address
```

#### 6. (Optional) Modify the jetcache cache configuration, see <a href="https://github.com/alibaba/jetcache/wiki" target="_blank">official documentation</a>


#### 7. (Optional) The project log file is modified in `core\src\main\resources\logback-spring.xml`


#### 8. Project packaging and deployment


```
·Execute in the project root directory mvn clean -Dmaven.test.skip=true package
·Then put the corresponding module under the target folder
·Upload ${name}-${version}-SNAPSHOT.zip/tar.gz to the server, for example: admin-0.0.1-SNAPSHOT.zip and decompress the compressed file, check the corresponding configuration file.

```


#### 9. Project start <br/>


```
First start nacos, seata, sentinel, elasticsearch, mysql, redis and other basic services
Start each module service by executing sh 72crm.sh start (directly run 72crm.bat under windows) under the file module decompressed in the eighth step.
Among them, the basic modules of the project: gateway, authorization, and admin must be started, and other modules can be started on demand.

After the startup is complete, visit: http://localhost:8443/ in the browser to log in to the system
```
#### 10. Initialize user information (serial number)<br/>


```
Visit http://localhost:8443/
Follow the prompts to initialize the super administrator account and password information, and fill in the serial number. After success, use the initialized administrator account to log in to the system to add other employees, assign permissions, etc.

Activation serial number:
6EA74C261C4BA344BC716FCD68295694BABFE016F5B7FA4890E4E29B0F52A5D965EE4A1AF633633D4573A2559630986F976D8F2920D688686CB60967F6FFB9FDADE6AC6DFD39416DE175D0DE01699C816244C16EE4E533A959E3ED0653143A7363E5B98E62126A78CDC5578636F456D29FD2B063FCBED837D50B10450C6FFBF0290DB782C8D4525864A96A98C37C0106FB5D8392A7E828F0BEFA86B4CD28BEBE83628A59BB23F60B7799A22C8D7B2039ED30F05492E9D2A2E2A03D7AC0199EA2CE529D561AE622B3C0DECC50D8A223BC5DA03E3AFF1150F0F217B0BE0400835369329DB74454870D5314DBA7C24B98CCE5600CBDAF264A21974FA3C85E7EAF0A

```

#### 11. Upgrade instructions and precautions<br/>

```
1、The back-end code update can directly download the full code and replace it
2、To update the database, please download DB/update/V11.x.x.sql, and execute it incrementally (for example, the current version is V11.0.1, and you need to execute V11.0.2.sql, V11.1.0.sql to upgrade to V11.1.0)
3、For docker update, please use online update or manually back up the database data and perform incremental upgrade SQL, then back it up locally, then download the new version of the docker image, and restore the SQL after the incremental upgrade to the new version of the database

```


### Other instructions

#### 1. Code generator and Swagger documentation<br/>


```
Code generator address: core\src\test\com\megazone\generator\Generator.java
Swagger document address `http://localhost:8443/doc.html`
```


#### 2. Module dependencies <br/>

```
- Except for the gateway, the rest of the projects depend on the admin module, which is used to obtain the information of the current login person
- The task of the oa module depends on the work module, and some other related business functions depend on the crm module and the examine module
- Business intelligence depends on crm, oa modules
```


#### 3. Build CRM docker image from source <br/>

Requirement: JDK 11
Pull code server, checkout develop branch then cd to root of project and run command:
```
./docker-build.sh
```

Pull code frontend, checkout develop branch then cd to root of project and run command:
```
docker build -t crm-frontend .
```
#### 4. Run Docker compose<br/>
`cd` to the root folder of CRM-Server project
* For AMD64 architecture:
```
cd docker

docker network create kuberix_crm_network
docker-compose up
```
* For ARM64 architecture:
1. Change Elasticsearch image: `registry.cn-hangzhou.aliyuncs.com/72crm/elasticsearch:6.8.6` -> `blacktop/elasticsearch:6.8.23`
2. Delete folder: `docker/data/elasticsearch/plugins`
3. Extract zip file `docker/analysis-icu-6.8.23.zip` to `docker/data/elasticsearch/plugins/analysis-icu-6.8.23`
4. Run commands:
```
cd docker

docker-compose up
```

Copyright 2022 © MEGAZONE CLOUD Corp. All Right Reserved.
