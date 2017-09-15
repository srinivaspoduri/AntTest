package Package1;

import org.testng.annotations.Test;

public class TestClass2 {
	
	@Test
	public void sample()
	{
		System.out.println("*****Sample in TestClass2**********");
		System.out.println("*****Sample in TestClass2**********");
		System.out.println("*****Sample in TestClass2**********");
	}
- hosts: launched
  gather_facts: no  
  vars:
    date: "{{ lookup('pipe', 'date +%Y%m%d-%H%M') }}"   
    javaargs: "-Xmx512M -Xss10M"
  tasks:   
   - pause:
       minutes: 25
   - name: Wait for EC2 instance to come up
     wait_for: host=localhost port=80 delay=10 timeout=120
   - name: Create node containers for test Execution
     shell: cd /apps/opt/workplace;docker-compose scale {{ Browser }}={{ InstanceCount }}
   - name: Launch Weave Scope for monitoring
     shell: scope launch
     ignore_errors: yes
   - name: Repo Installation 
     git: repo="{{ ScriptRepoUrl }}" dest="/apps/opt/workplace/code" force="yes" version="{{ Branch }}"
     register: git_deploy
     until: git_deploy|succeeded     
   - name: Test Scripts Build and Execution
     shell: cd /apps/opt/workplace/code/{{ BuildXMLPath }};(ant init compile Run  > /apps/opt/workplace/code/testlog.txt)
     async: 18000
     poll: 30
     ignore_errors: yes   
     register: test_run
   - name: Upload to Log file
     shell: curl -i -u v724727:APBJfo5JFTnVYr5uDuU9pBna7Aq -T /apps/opt/workplace/code/testlog.txt "https://oneartifactory.verizon.com/artifactory/TCEV_NTQEPLATFORM/REPORTS/{{ AppName }}/{{ date }}/testlog.txt"     
   - name: AWS Selenium Test Report
     mail:        
        host: smtp.verizon.com
        port: 25
        subject: Test Execution Report
        body: Please find attached Selenium Test Report in Ant build
        from: "{{ MailFrom }}"
        to: "{{ MailTo }}"
        attach: /apps/opt/workplace/code/{{ ReportPath }}
   - name: Upload to Artifactory
     shell: curl -i -u v724727:APBJfo5JFTnVYr5uDuU9pBna7Aq -T /apps/opt/workplace/code/{{ ReportPath }} "https://oneartifactory.verizon.com/artifactory/TCEV_NTQEPLATFORM/REPORTS/{{ AppName }}/{{ date }}/{{ ReportPath }}"
}
