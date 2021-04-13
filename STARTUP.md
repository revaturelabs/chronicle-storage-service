# Running Chronicle Front

Please review README.md included in this repository for information on how to get started with contributing.

## Running on Local Host:

To start your application, ensure you have the latest repo for [chronicle-front](https://github.com/919-BenAraythel-Chronicle/chronicle-front) and [chronicle-storage-service](https://github.com/919-BenAraythel-Chronicle/chronicle-storage-service)

_You'll need to ensure that you have both an **aws.properties** file as well as a **firebase-service-credentials.json** file in your resources folder. Since those files are referenced in the .gitignore file, you'll need to receive them from your instructor._

The aws.properties file should have the following fields. Please replace the question marks with the actual keys that you receive from the product owner. You may need to create your own aws.properties file.
aws.access_key_id = ?
aws.secret_access_key = ?
aws.s3.region = us-east-1
aws.s3.bucket = s3://chronicle-p3

The firebase-service-credentials.json file may be called something different when you receive it from the product owner.

Then follow the README.md for each of the separate repositories to set up your IDE.
NOTE: Both the java application and the angular application must be running for the application to properly work.

Defaults are localhost:4200 for the angular application and localhost:8080 for the java application.

## Running Through Docker (Most likely for DevOps purposes)

If you'd like to run the application(s) through docker, first ensure that you have [Docker Desktop](https://www.docker.com/products/docker-desktop) installed.

Next, navigate to the root of each project folder. You'll notice that each project has a Dockerfile at the root. Additionally, the front-end project has an Nginx configuration file.

### Docker: Back-end Image Creation and Container

To create an image and build a container for the Chronicle Storage Service, use the following command in your terminal:

```
docker build -t chronicle-storage-image .
```

After the image has been created, use the following command to create a container:

```
docker run --name chronicle-storage-container -d -p 10000:8080 chronicle-storage-image
```

The storage service should now be up and running from the container through localhost.

### Docker: Front-end Image Creation and Container

To create an image and build a container for the Chronicle Front-end, use the following command in your terminal:

```
docker build -t chronicle-front-image .
```

After the image has been created, use the following command to create a container:

```
docker run --name chronicle-angular -d -p 80:80 chronicle-front-image
```

The storage service should now be up and running from the container through localhost.

### DevOps Documentation

Documentation regarding DevOps can be found [here](https://docs.google.com/document/d/1BkgVkMa7aWO8WY3zZo_ClPPBCZQtGbkbkL5iU0HmvU8/edit?usp=sharing).
