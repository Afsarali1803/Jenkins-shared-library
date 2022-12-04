def sonarChecks() {
        stage('sonarcheck') {
                sh "echo Starting Code Quality Analysis"
                //sh "sonar-scanner -Dsonar.host.url=http://172.31.10.167:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} ${ARGS}"
                //sh "sonar-scanner -Dsonar.host.url=http://172.31.10.167:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} ${ARGS}"
                //sh "bash -x sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONARURL} ${COMPONENT}" 
                sh "echo Code Quality Analysis is Completed"
        }
}

def lintChecks() {
        stage('Lint check')  {
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
                        sh "echo doing generic lint check"
        
        }
}

def testCases() {
 parallel(
                "UNIT": {
                    stage("Unit Testing") {
                        echo "Unit Testing Compleyed"
                           }
                     },
               "INTEGRATION": {
                    stage("Integration Testing") {
                        echo "Integration Testing"
                           }
                    },
               "FUNCTIONAL": {
                    stage("Functional Testing") {
                        echo "Functional Testing"
                           }
                    },
                )
        }  

def artifacts() {
        stage('Check Artifacts') {
           env.UPLOAD_STATUS=sh(returnStdout: true, script: 'curl -L -s http://${NEXUSURL}:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip ||  true' )
           print UPLOAD_STATUS                
        }
        
        if(env.UPLOAD_STATUS == "") {
                stage('Prepare Artifacts'){
                  if(env.APPTYPE == "nodejs") {
                        sh "echo nodejs"
                        }
                  else if(env.APPTYPE == "maven") {
                        sh  "echo maven"
                        }
                  else if(env.APPTYPE == "python") {
                        sh  "echo python"
                        }
                  else if(env.APPTYPE == "angularjs") {
                        sh "echo Angular"
                        }
                  else  {
                        sh "echo lang"
                        }
                }
                stage('Upload Artifacts') {
                        withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'NEXUS_PSW', usernameVariable: 'NEXUS_USR')]) {              
                                 sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUSURL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"  
                        }
                }
        }
}

        



