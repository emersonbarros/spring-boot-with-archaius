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
   def sonarqubeScannerHome = tool name: 'SonarQubeScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
   sh "${sonarqubeScannerHome}/bin/sonar-scanner -e -Dsonar.host.url=http://192.168.252.46:9001 -Dsonar.analysis.mode=preview -Dsonar.issuesReport.console.enable=true -Dsonar.issuesReport.html.enable=true -Dsonar.projectName=commons-develop -Dsonar.projectVersion=0.0.1 -Dsonar.projectKey=commons:develop -Dsonar.sources=."
    
   junit testResults: '**/surefire-reports/*.xml'
  }
}
