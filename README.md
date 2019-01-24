# omar-upload

## Description

The OMAR Upload service allows users to upload image files for staging

[![Build Status](https://jenkins.ossim.io/buildStatus/icon?job=omar-upload-dev)]()

#### The Upload Web Service takes a single image and uploads it to OMAR

### Required environment variable
- OMAR_COMMON_PROPERTIES

### Optional environment variables
Only required for Jenkins pipelines or if you are running Nexus and/or Openshift locally

- OPENSHIFT_USERNAME
- OPENSHIFT_PASSWORD
- REPOSITORY_MANAGER_USER
- REPOSITORY_MANAGER_PASSWORD

## How to Build/Install omar-upload-app locally

1. Git clone the following repos or git pull the latest versions if you already have them.
```
  git clone https://github.com/ossimlabs/omar-common.git
  git clone https://github.com/ossimlabs/omar-core.git
  git clone https://github.com/ossimlabs/omar-upload.git
```

2. Set OMAR_COMMON_PROPERTIES environment variable to the omar-common-properties.gradle (it is part of the omar-common repo).

3. Install omar-core-plugin (it is part of the omar-core repo).
```
 cd omar-core/plugins/omar-core-plugin
 gradle clean install
```

4. Install omar-upload-plugin
```
 cd omar-upload/plugins/omar-upload-plugin
 gradle clean install
```

5. Build/Install omar-upload-app
#### Build:
```
 cd omar-upload/apps/omar-upload-app
 gradle clean build
 ```
#### Install:
```
 cd omar-upload/apps/omar-upload-app
 gradle clean install
```

...
...
