def sonarChecks() {
        sh "echo Starting Code Quality Analysis"
        sh "-Dsonar-scanner -Dsonar.host.url=http://172.31.10.167:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} ${ARGS}"
        //sh "sonar-scanner -Dsonar.host.url=http://172.31.10.167:9000 -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} -Dsonar.projectKey=${COMPONENT} ${ARGS}"
        sh "bash -x sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONARURL} ${COMPONENT}" 
        sh "echo Code Quality Analysis is Completed"
}