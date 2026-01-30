//def buildJar() {
//
//    echo 'building the application ....'
//    echo "building version ${params.VERSION}"
//
////    sh 'mvn package'
//}

//def buildImage() {
//    echo "building the docker image..."
//
//    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
//        sh 'docker build -t chidi123/quiz-app:jma-2.0 .'
//        sh "echo $PASS docker login -u $USER --password-stdin"
//        sh "docker push chidi123/quiz-app:jma-2.0"
//    }
//
//}

def incrementAppVersion(){
    sh "mvn build-helper:parse-version versions:set \
                   -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
                   version:commit"

    def matcher = readFile('pom.xml') =~ '<version>(.*)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
}
//
//def buildImage() {
//
//    buildImage "chidi123/quiz-app:$IMAGE_NAME"
//    dockerLogin()
//    dockerPush "chidi123/quiz-app:$IMAGE_NAME"
//}


def testApp() {



    echo 'testing the application ....'



    echo "Executing pipeline for branch $BRANCH_NAME"

    sh 'mvn clean test'

}

def deployApp() {

    echo 'deploying the application....'
//    echo "deploying version ${params.VERSION}"

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


def githubCommit(){

    withCredentials([usernamePassword(credentialsId: 'github-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'git config --global user.email "jenkins@example.com"'
        sh 'git config --global user.name "jenkins"'

        sh 'git status'
        sh 'git branch'
        sh 'git config --list'
        sh "git remote set-url origin https://${USER}:${PASS}@github.com/Alex1-ai/Quiz-App.git"

        sh 'git add .'
        sh 'git commit -m "ci: version bump"'
        sh 'git push origin HEAD:jenkins-jobs'
    }
}

return this