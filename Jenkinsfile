#!groovy
node {
  // Need to replace the '%2F' used by Jenkins to deal with / in the path (e.g. story/...)
  // because tests that do getResource will escape the % again, and the test files can't be found.
  // See https://issues.jenkins-ci.org/browse/JENKINS-34564 for more.
  ws("workspace/${env.JOB_NAME}/${env.BRANCH_NAME}".replace('%2F', '_')) {
    // Mark the code checkout 'stage'....
    stage 'Checkout'
    checkout scm

    // Mark the code build 'stage'....
    stage 'Build'
    withMaven(maven: 'Maven') {
        sh "mvn clean verify -B"
    }
    
   stage 'Sonar'
   withMaven(maven: 'Maven') {  
      sh '''
        BASE_URL="localhost"
        PORT="9000"
        PROJECT="teste"
        NAME="${env.JOB_NAME}"
        BRANCH="${env.BRANCH_NAME}"
        GATE_ID="1"

        PROJECT_ID=`curl -vs -i -X POST http://$BASE_URL:$PORT/api/projects/create -d key="$PROJECT" -d name="$NAME" -d branch="$BRANCH" 2>&1 | sed -n 's|.*"id":"\([^"]*\)".*|\1|p'`

        echo "'$PROJECT_ID' associated to quality gate '$GATE_ID' (If project id is null, it was already created or an error occurred)"
        RESPONSE=`curl -s -u admin:admin -X POST http://$BASE_URL:$PORT/api/qualitygates/select -d gateId="$GATE_ID" -d projectId="$PROJECT_ID"`
      '''
   }

   junit testResults: '**/surefire-reports/*.xml'
  }
}
