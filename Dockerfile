FROM tomcat:9
MAINTAINER matveyshnytkin@gmail.com
ADD target/MainServlet.war /usr/local/tomcat/webapps/
#ADD target/ROOT.war /usr/local/tomcat/webapps/
#RUN rm -rf /usr/local/tomcat/webapps/ROOT
CMD ["catalina.sh", "run"]