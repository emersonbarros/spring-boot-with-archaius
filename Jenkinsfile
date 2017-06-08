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
   sh '''
     GIT_BRANCH=`git rev-parse --abbrev-ref HEAD`
     SONAR_BRANCH=`printf '%s' $GIT_BRANCH | sed s/[^0-9a-zA-Z:_.\\-]/'_'/g`
     echo "GIT_BRANCH=${GIT_BRANCH}" > my-build-vars.properties
     echo "SONAR_BRANCH=${SONAR_BRANCH}" >> my-build-vars.properties
   '''    
   def props = getBuildProperties("my-build-vars.properties")
   echo "my-build-vars.properties=${props}"
   def sonarBranchParam = getSonarBranchParameter(props.getProperty('SONAR_BRANCH'))
   withMaven(maven: 'Maven') {
      sh "mvn sonar:sonar ${sonarBranchParam} -Dsonar.jdbc.url='${sonarDatabaseUrl}' -Dsonar.host.url=${sonarServerUrl} -Dsonar.jdbc.username=${sonarDatabaseLogin} -Dsonar.jdbc.password=${sonarDatabasePassword}"
    }
    junit testResults: '**/surefire-reports/*.xml'
  }
}
