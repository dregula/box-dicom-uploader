## Java DICOM uploader for Box.com

This is a work-in-progress (eg. alpha) version of a Java-based DICOM uploader for Box.com 

### Requirements

In order to run this application, you will need to create a Box.com developer account at [developer.box.com](http://developer.box.com)

It currently assumes your DICOM directories use the following tree structure:

```bash
dicom
├── study #1
│   ├── series
│   │   └── instance
│   │   └── ...
├── study #2
│   ├── series
│   │   └── instance
│   │   └── ...
```

This tree structure increases performance for creating study.boxdicom JSON files.


### Usage

Download the source and compile:

```bash
mvn package
```

And run the program with your Box developer credentials as environment variables:

```bash
BOX_CLIENT_ID=client_id \
BOX_CLIENT_SECRET=secret \
java -jar target/box-dicom-uploader-1.0-SNAPSHOT-fat.jar 
```

The app should be running: 

```bash
Nov 30, 2018 2:15:23 PM com.github.susom.boxdicomuploader.WebServer
INFO: HTTP server running on port 8000
Nov 30, 2018 2:15:23 PM io.vertx.core.impl.launcher.commands.VertxIsolatedDeployer
INFO: Succeeded in deploying verticle
```

And you can login using your regular Box.com account and upload to Box by visiting [http://localhost:8000](http://localhost:8000)

