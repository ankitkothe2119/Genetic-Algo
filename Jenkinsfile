pipeline {
    agent any

    tools {
        maven 'Maven-3'   // Name configured in Jenkins Global Tool Configuration
    }

    environment {
        PROJECT_DIR = '1_College_Project_Java'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '--- Stage 1: Pulling code from GitHub ---'
                git branch: 'Test-Implimantation',
                    url: 'https://github.com/ankitkothe2119/Genetic-Algo.git'
            }
        }

        stage('Build') {
            steps {
                echo '--- Stage 2: Compiling Java source code via Maven ---'
                dir("${PROJECT_DIR}") {
                    bat 'mvn clean compile'  // Use 'sh' for Linux Jenkins
                }
            }
        }

        stage('Test') {
            steps {
                echo '--- Stage 3: Running Unit Tests ---'
                dir("${PROJECT_DIR}") {
                    bat 'mvn test'
                }
            }
        }

        stage('Package') {
            steps {
                echo '--- Stage 4: Creating WAR package ---'
                dir("${PROJECT_DIR}") {
                    bat 'mvn package -DskipTests'
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo '--- Stage 5: Archiving build artifacts ---'
                archiveArtifacts artifacts: "${PROJECT_DIR}/target/*.war", fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Build completed successfully!'
        }
        failure {
            echo 'Build failed. Check logs above for errors.'
        }
        always {
            echo "Pipeline finished. Build #${env.BUILD_NUMBER}"
        }
    }
}
