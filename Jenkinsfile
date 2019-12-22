pipeline {
	agent {
		label "linux-amd64"
	}
	stages {
		stage('Checkout') {
			steps {
				checkout([
					$class: 'GitSCM',
					branches: [[name: '*/master']],
					doGenerateSubmoduleConfigurations: false,
					extensions: [],
					submoduleCfg: [],
					userRemoteConfigs: [[
						credentialsId: 'kremi151_github',
						url: 'git@github.com:kremi151/3D-Tic-Tac-Toe-for-Android.git'
					]]
				])
			}
		}
		stage('Build') {
			steps {
				sh 'chmod +x gradlew'
				sh 'ANDROID_HOME=/usr/lib/android-sdk ./gradlew assemble'
			}
		}
	}
	post {
		always {
			archiveArtifacts artifacts: 'app/build/outputs/apk/release/*.apk',
			onlyIfSuccessful: true
		}
	}
}
