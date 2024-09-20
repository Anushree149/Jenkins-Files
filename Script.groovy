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
              sh "sudo mv . /var/www/html/" 
            } 
        } 
    } 
}