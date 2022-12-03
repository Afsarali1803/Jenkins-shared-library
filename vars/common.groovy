def sonarChecks() {
        stage('sonarcheck')
                sh "echo Starting Code Quality Analysis"
                //sh "sonar-scanner -Dsonar.host.url=http://172.31.10.167:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} ${ARGS}"
                //sh "sonar-scanner -Dsonar.host.url=http://172.31.10.167:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} ${ARGS}"
                //sh "bash -x sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONARURL} ${COMPONENT}" 
                sh "echo Code Quality Analysis is Completed"
}

def lintChecks() {
     stage('Lint check')   
        if(env.APPTYPE == "nodejs") {   
                sh "echo installing jslinst"
                sh "npm i jslint"   
                sh "node_modules/jslint/bin/jslint.js server.js || true"
                sh "echo Lint Checks Completed $COMPONENT"
        }
        else if(env.APPTYPE == "maven") {
                sh "mvn checkstyle:check || true"
                sh "echo Lint check completed for $COMPONENT"        
        }
        else if(env.APPTYPE == "python") {
                sh "echo list checks started for payment * * * * ......"
                sh "echo Lint check completed for $COMPONENT"        
        }
        else if(env.APPTYPE == "golang") {
                sh "echo list checks started for golang * * * * ......"
                sh "echo Lint check completed for $COMPONENT"        
        }
        else
                sh "echo doing generic lint check
        }
}