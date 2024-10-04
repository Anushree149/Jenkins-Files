pipeline { 
    agent any 

    stages { 
        stage('Git Clone') { 
            steps {
                git branch: "main", url:'https://github.com/Anushree149/JournalEntry.git' 
            } 
        } 
        stage('Deploy') { 
            steps {
                sh "sudo mv * /var/www/html/" 
            } 
        } 
        stage('Restart VM') {
            steps {
                sh "sudo systemctl restart nginx"
            }
        }
    }

    post {
        success {
            emailext body: '''<html><body><h2>journalentry deployment - Version # $BUILD_NUMBER - Deployment $BUILD_STATUS.</h2><br/>
                            <br/>
                            <h2>Deployed on apache2.com</h2>
                            <br/>
                           <h3><b> Check console <a href="$BUILD_URL">output</a> to view full results.</b></h3><br/>
                            <b><i>If you cannot connect to the build server, check the attached logs.</i></b><br/>
                            <br/>
                            --<br/>
                            Following is the last 50 lines of the log.<br/>
                            <br/>
                            <b>--LOG-BEGIN--</b><br/>
                            <pre style='line-height: 22px; display: block; color: #333; font-family: Monaco,Menlo,Consolas,"Courier New",monospace; padding: 10.5px; margin: 0 0 11px; font-size: 13px; word-break: break-all; word-wrap: break-word; white-space: pre-wrap; background-color: #f5f5f5; border: 1px solid #ccc; border: 1px solid rgba(0,0,0,.15); -webkit-border-radius: 4px; -moz-border-radius: 4px; border-radius: 4px;'>
                            ${BUILD_LOG, maxLines=50, escapeHtml=true}
                            </pre>
                            <b>--LOG-END--</b></body></html>''',
            subject: 'Journal entry Deployed Successfully - $PROJECT_NAME',
            to: 'abdallahq989@gmail.com , idekaranushree@gmail.com',
            mimeType: 'text/html'
        }

        failure {
            emailext body: '''<html><body><h2>LMS PROD - Build # $BUILD_NUMBER - Deployment $BUILD_STATUS.</h2><br/>
                            <br/>
                           <b> Check console <a href="$BUILD_URL">output</a> to view full results.</b><br/>
                            <b><i>If you cannot connect to the build server, check the attached logs.</i></b><br/>
                            <br/>
                            --<br/>
                            Following is the last 50 lines of the log.<br/>
                            <br/>
                            <b>--LOG-BEGIN--</b><br/>
                            <pre style='line-height: 22px; display: block; color: #333; font-family: Monaco,Menlo,Consolas,"Courier New",monospace; padding: 10.5px; margin: 0 0 11px; font-size: 13px; word-break: break-all; word-wrap: break-word; white-space: pre-wrap; background-color: #f5f5f5; border: 1px solid #ccc; border: 1px solid rgba(0,0,0,.15); -webkit-border-radius: 4px; -moz-border-radius: 4px; border-radius: 4px;'>
                            ${BUILD_LOG, maxLines=50, escapeHtml=true}
                            </pre>
                            <b>--LOG-END--</b></body></html>''',
            subject: 'Journal Entry Deployment failed: $PROJECT_NAME', 
            to: 'abdallahq989@gmail.com , idekaranushree@gmail.com',
            mimeType: 'text/html'
        }
    }
}
