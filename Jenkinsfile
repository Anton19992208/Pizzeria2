pipeline {
	agent any

	tools{
            maven 'Maven'
	}

	stages {

		stage('Build'){
			steps {
			       dir('mssc-pizza-service') {
			       sh 'mvn clean install -DskipTests'
                             }
			       dir('mssc-pizza-inventory') {
			       sh 'mvn clean install -DskipTests'
                             }
			       dir('mssc-pizza-order') {
			       sh 'mvn clean install -DskipTests'
                             }


			}
		}

		stage('Test'){
			steps{
			       dir('mssc-pizza-service') {
			       sh 'mvn test'
                             }
			       dir('mssc-pizza-inventory') {
			       sh 'mvn test'
                             }
			       dir('mssc-pizza-order') {
			       sh 'mvn test'
                             }
			}
		}

		stage('Deploy') {
			steps {
			      dir('mssc-pizza-service') {
			       sh 'mvn jar:jar deploy:deploy'
                             }
			       dir('mssc-pizza-inventory') {
			       sh 'mvn jar:jar deploy:deploy'
                             }
		               dir('mssc-pizza-order') {
                               sh "pwd"
			       sh 'mvn clean install -DskipTests'
                             }
			}
		}
	}
}