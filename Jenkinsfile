pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven'
        DISCORD_WEBHOOK_URL = 'https://discord.com/api/webhooks/YOUR_WEBHOOK_URL'
    }
    stages {
        stage('Checkout') {
            steps {
                // Clone the repository dynamically based on the branch or PR
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
    post {
        success {
            script {
                // Send success notification to Discord
                def message = """
                **Build Successful**
                Project: GestionBibliotheque
                Branch: ${env.BRANCH_NAME ?: 'main'}
                """
                sendDiscordNotification(message)
            }
        }
        failure {
            script {
                // Send failure notification to Discord
                def message = """
                **Build Failed**
                Project: GestionBibliotheque
                Branch: ${env.BRANCH_NAME ?: 'main'}
                """
                sendDiscordNotification(message)
            }
        }
    }
}

def sendDiscordNotification(message) {
    def payload = """
    {
        "content": "${message}"
    }
    """
    // Send POST request to Discord webhook
    httpRequest acceptType: 'APPLICATION_JSON', contentType: 'APPLICATION_JSON', url: 'https://discord.com/api/webhooks/1319347656891564032/wFFq8BIl5WhDPRyeBuwmzYCNmWt8pt0d8UpqIlYFJTksuOhK2cmCv0tq4I4PDcEUQGnj', httpMode: 'POST', requestBody: payload
}
