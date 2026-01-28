def buildApp() {

    echo 'building the application ....'
    echo "building version ${params.VERSION}"

//    sh 'mvn package'
}

def buildDockerImage() {
    echo "building the docker image..."
//    sh ""

}


def testApp() {

    echo 'testing the application ....'

    sh 'mvn test'
}

def deployApp() {
    echo 'deploying the application....'
    echo "deploying version ${params.VERSION}"

//                echo "deploying with ${SERVER_CREDENTIALS}"
//
//                sh "${SERVER_CREDENTIALS}"
//                withCredentials([
//                   usernamePassword(credentials: 'server-credentials', usernameVariable: USER, passwordVariable: PWD )
//                ]){
//                    sh "some script ${USER} ${PWD}"
//
//                }

}


return this