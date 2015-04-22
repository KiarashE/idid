- To deploy to Heroku
mvn clean heroku:deploy-war

- To open the main web page by command line
heroku open -a protected-caverns-5257


 curl -d '{"docs":[{"name":"not namn"}]}' -X POST  http://edutime.cloudant.com/idid/_bulk_docs -H "Content-Type:application/json"