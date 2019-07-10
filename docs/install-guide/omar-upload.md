# OMAR Upload

## Purpose
The OMAR-upload service enables uploading of imagery.

## Installation in Openshift

**Assumption:** The omar-upload-app docker image is pushed into the OpenShift server's internal docker registry and available to the project.

### Persistent Volumes

OMAR-upload requires shared access to OSSIM imagery data. This data is assumed to be accessible from the local filesystem of the pod. Therefore, a volume mount must be mapped into the container. A PersistentVolumeClaim should be mounted to `/data`, but can be configured using the deploymentConfig.

### Environment variables

|Variable|Value|
|------|------|
|SPRING_PROFILES_ACTIVE|Comma separated profile tags (*e.g. production, dev*)|
|SPRING_CLOUD_CONFIG_LABEL|The Git branch from which to pull config files (*e.g. master*)|

## Using the service

The upload service provides an endpoint for uploading imagery which can be accessed on the [API Swagger page](https://omar-dev.ossim.io/omar-upload/api)

Notes for `<api>/upload/upload`:
>Currently, only certain image types are supported, and only single images at a time.

_[More examples](https://omar-dev.ossim.io/omar-upload/api)_
