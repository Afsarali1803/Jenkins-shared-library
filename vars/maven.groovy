def lintChecks() {
    sh "echo list checks started for payment * * * * ......"
    sh "echo Lint Checks Completed for $COMPONENT"
}

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
                        common.lintChecks()                  // Use script { when you're using groovy based conventions }
                            }
                        }
                    }   
            stage('Sonar Checks') {
                steps {
                    script {
                        sh "mvn clean compile"
                        env.ARGS="-Dsonar.java.binaries=target/"
                        common.sonarChecks()                  // Use script { when you're using groovy based conventions }
                            }
                        }
                    } 

            stage('Test Cases') {
                parallel {
                    stage('Unit Testing') {                 
                        steps {
                            sh "echo Unit Testing Completed"   
                                }
                            }
                    stage('Integration Testing') {                 
                        steps {
                            sh "echo Integration Testing Completed"   
                                }
                            }
                    stage('Function Testing') {                 
                        steps {
                            sh "echo Function Testing Completed"   
                                }
                            }
                        }
                    }
            stage('Versioning the artifacts') {
                    steps {
                        sh "echo Versioning the artifacts"
                            }
                        }                
            }
        
    }  // end of pipelines
}// end of call


