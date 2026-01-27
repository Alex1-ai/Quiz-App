

pipeline {
    agent any

    parameters {
        string(name: 'VERSION', choices: ['1.1.0', '1.2.0', '1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true, description:'')


    }

//     tools {
//        maven 'Maven'
//     }
//
//     environment {
//         NEW_VERSION = '1.3.0'
//
//         SERVER_CREDENTIALS = credentials('server-cred')
//
//     }



    stages {

        stage("test") {
            when {
                 expression {
                     params.executeTests
                 }

            }
            steps {
                echo 'testing the application'

            }

        }


        stage("build") {
            steps {
               echo 'building the application....'
               echo "building version ${NEW_VERSION}"
            }

        }


        stage("deploy") {
            steps {
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

        }
    }
//     post {
//         always {
//
//         }
//         success {
//
//         }
//         failure {
//
//         }
//
//
//     }


}