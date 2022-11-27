def lintChecks() {
    sh "echo list checks started for payment * * * * ......"
    sh "echo Lint Checks Completed for $COMPONENT"
}

// function call will be called by default, when you call the fileName
def call() {
    pipeline{ 
        stages {
            stage('Lint Checks') {
                steps {
                    script {
                        lintChecks()                  // Use script { when you're using groovy based conventions }
                    }
                }
            }

        }   // end of stages 
    }  // end of pipelines
} // end of call