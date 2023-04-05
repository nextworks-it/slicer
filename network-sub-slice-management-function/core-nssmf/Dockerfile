####################################
### BUILD PART SKIP FOR NOW ########
####################################

FROM ubuntu:20.04 

#non interactive tzdata config
ENV DEBIAN_FRONTEND=noninteractive
# Update Ubuntu software repository and install prerequisites
RUN apt-get update && apt-get install -y openjdk-8-jdk
    #wget \
    # openjdk-8-jdk \
    #sudo
#COPY --from=nsmf-builder /home/ubuntu/nsmf/nsmf-app/target/nssmf-app-0.0.1.jar /home/ubuntu/
COPY ./nssmf-app/target/nssmf-app-0.0.1.jar /home/ubuntu/
