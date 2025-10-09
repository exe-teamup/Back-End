pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven 3.9.9'
    }

    environment {
        // Đọc file .env và set biến môi trường
        // (Jenkins không hỗ trợ trực tiếp đọc .env nên ta làm thủ công bằng shell)
        ENV_FILE = '.env'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/exe-teamup/Back-End.git'
            }
        }

        stage('Load .env') {
            steps {
                script {
                    if (fileExists(ENV_FILE)) {
                        echo "✅ Loading environment variables from ${ENV_FILE}"
                        // Xuất biến môi trường từ file .env
                        sh '''
                        set -a
                        source ${ENV_FILE}
                        set +a
                        printenv | grep DB_
                        '''
                    } else {
                        error "❌ .env file not found!"
                    }
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Package') {
            steps {
                echo '✅ Maven build success!'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t exe-teamup-app .'
            }
        }

        stage('Docker Run') {
            steps {
                // Dừng container cũ (nếu đang chạy)
                sh '''
                docker stop exe-teamup-app-container || true
                docker rm exe-teamup-app-container || true
                '''

                // Chạy container mới với biến môi trường từ file .env
                sh '''
                docker run -d --name exe-teamup-app-container \
                  --env-file .env \
                  -p 8081:8080 exe-teamup-app
                '''
            }
        }
    }
}
