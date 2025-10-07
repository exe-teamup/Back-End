pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven 3.9.9'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/exe-teamup/Back-End.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        //stage('Test') {
        //    steps {
        //        sh 'mvn test'
        //    }
        //}

        stage('Package') {
            steps {
                echo 'Build success!'
            }
        }
    }
}
