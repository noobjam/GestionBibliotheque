pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven'
    }
    stages {
        stage('Checkout') {
            steps {

                script {
                    if (env.BRANCH_NAME) {
                        echo "Building branch: ${env.BRANCH_NAME}"
                        git branch: "${env.BRANCH_NAME}", url: 'https://github.com/noobjam/GestionBibliotheque.git'
                    } else {
                        echo "Building main branch"
                        git branch: 'main', url: 'https://github.com/noobjam/GestionBibliotheque.git'
                    }
                }
            }
        }
        stage('Build') {
            steps {
                bat "\"${MAVEN_HOME}\\bin\\mvn\" clean compile"
            }
        }
        stage('Test') {
            steps {
                bat "\"${MAVEN_HOME}\\bin\\mvn\" test"
            }
        }
        stage('Quality Analysis') {
            steps {
                withSonarQubeEnv('sonar-server') {
                    bat "\"${MAVEN_HOME}\\bin\\mvn\" sonar:sonar " +
                        "-Dsonar.projectKey=GestionBibliotheque " +
                        "-Dsonar.projectName='GestionBibliotheque' " +
                        "-Dsonar.host.url=http://localhost:9000 " +
                        "-Dsonar.token=sqp_aca1c401629c233c2a27e148a11b8663837cf798"
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Déploiement simulé réussi'
            }
        }
    }
//     post {
//         success {
//             emailext to: 'elqajjam@gmail.com',
//                 subject: 'Build Success',
//                 body: 'Le build a été complété avec succès.'
//         }
//         failure {
//             emailext to: 'elqajjam@gmail.com',
//                 subject: 'Build Failed',
//                 body: 'Le build a échoué.'
//         }
//     }
}
