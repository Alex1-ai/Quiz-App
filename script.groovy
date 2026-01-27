def buildApp() {

    echo 'building the application ....'
    echo "building version ${params.VERSION}"
}


def testApp() {

    echo 'testing the application ....'
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