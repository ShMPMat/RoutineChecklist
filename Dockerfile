FROM tomcat:9
MAINTAINER matveyshnytkin@gmail.com
ENV DB_PASSWORD 1234

ADD target/MainServlet.war /usr/local/tomcat/webapps/
ADD .docker-files/tomcat/conf/ /usr/local/tomcat/conf/
ADD .docker-files/run.sh /usr/local/tomcat
CMD /usr/local/tomcat/run.sh $DB_PASSWORD