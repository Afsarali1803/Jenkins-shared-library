def lintChecks() {
        sh "echo installing jslinst"
        sh "npm i jslint"   
        sh "node_modules/jslint/bin/jslint.js server.js || true"
        sh "echo Lint Checks Completed $COMPONENT"
}
// call is the function which will be called by default.
// function call will be called by default, when you call the fileName
def call() {
    pipeline{
        agent any
        environment {
            SONAR    = credentials('SONAR')
            SONARURL = "172.31.10.167"
        }
        stages {
            stage('Lint Checks') {
                steps {
                    script {
                        lintChecks()                  // Use script { when you're using groovy based conventions }
                    }
                }
            }   
            stage('Sonar Checks') {
                steps {
                    script {
                        env.ARGS="-Dsonar.sources=."
                        common.sonarChecks()                  // Use script { when you're using groovy based conventions }
                    }
                }
            } 

            stage('ABC Checks') {
                steps {
                    sh "echo Performing XYZ Checks"
                    sh "echo this takes 30 mins"
                }
            } 
        }   // end of stages 
    }  // end of pipelines
}//end of call