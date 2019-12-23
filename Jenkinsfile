void setBuildStatus(String message, String state) {
	step([
		$class: "GitHubCommitStatusSetter",
		reposSource: [$class: "ManuallyEnteredRepositorySource", url: env.GIT_URL],
		commitShaSource: [$class: "ManuallyEnteredShaSource", sha: env.GIT_COMMIT],
		contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
		errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
		statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
	]);
}

pipeline {
	agent {
		label "linux-amd64"
	}
	stages {
		stage('Notify GitHub') {
			steps {
				setBuildStatus('Build in pending', 'PENDING')
			}
		}
		stage('Build') {
			steps {
				sh 'chmod +x gradlew'
				sh './gradlew assembleRelease -x test'
			}
		}
	}
	post {
		always {
			archiveArtifacts artifacts: 'app/build/outputs/apk/release/*.apk',
			onlyIfSuccessful: true
		}
		success {
			setBuildStatus('Build succeeded', 'SUCCESS')
		}
		failure {
			setBuildStatus('Build failed', 'FAILURE')
		}
		unstable {
			setBuildStatus('Build is unstable', 'UNSTABLE')
		}
	}
}
