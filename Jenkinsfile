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

    sh "mvn clean verify -B"
    
    junit testResults: '**/surefire-reports/*.xml'
  }
}
