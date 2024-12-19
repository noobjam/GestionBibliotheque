pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven'
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/noobjam/GestionBibliotheque.git'
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
                    bat "\"${MAVEN_HOME}\\bin\\mvn\" sonar:sonar \
                                                   -Dsonar.projectKey=GestionBibliotheque \
                                                   -Dsonar.projectName='GestionBibliotheque' \
                                                   -Dsonar.host.url=http://localhost:9000 \
                                                   -Dsonar.token=sqp_bb1264b96c3f28ec385abc4ee0ec1b8141a34f94
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
