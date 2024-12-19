pipeline {
    agent any
    environment {
        MAVEN_HOME = tool 'Maven' // Define Maven as a tool from Jenkins tool configurations
    }
    stages {
        stage('Checkout') {
            steps {
                // Clone the repository from the specified branch and URL
                git branch: 'main', url: 'https://github.com/noobjam/GestionBibliotheque.git'
            }
        }
        stage('Build') {
            steps {
                // Run Maven clean and compile commands
                bat "\"${MAVEN_HOME}\\bin\\mvn\" clean compile"
            }
        }
        stage('Test') {
            steps {
                // Run Maven tests
                bat "\"${MAVEN_HOME}\\bin\\mvn\" test"
            }
        }
        stage('Quality Analysis') {
            steps {
                // Integrate with SonarQube for code quality analysis
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
                // Simulate a successful deployment
                echo 'Déploiement simulé réussi'
            }
        }
    }
    // Uncomment the post section if you want to add notifications for build success or failure
    // post {
    //     success {
    //         emailext to: 'elqajjam@gmail.com',
    //             subject: 'Build Success',
    //             body: 'Le build a été complété avec succès.'
    //     }
    //     failure {
    //         emailext to: 'elqajjam@gmail.com',
    //             subject: 'Build Failed',
    //             body: 'Le build a échoué.'
    //     }
    // }
}
