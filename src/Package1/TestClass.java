package Package1;

import org.testng.annotations.Test;

public class TestClass {
	
	@Test
	public void sample()
	{
		System.out.println("*****Sample in TestClass**********");
		System.out.println("*****Sample in TestClass**********");
		System.out.println("*****Sample in TestClass**********");
	}
	{
  "AWSTemplateFormatVersion":"2010-09-09",
  "Description":"",
  
  "Parameters" : {
    "AppID" : {
      "Type" : "String"
    } ,
    "UserID" : {
      "Type" : "String"
    } ,
    "Role" : {
      "Type" : "String"
    } ,
	"InstanceName" : {
      "Type" : "String"
    } ,
	"InstanceType" : {
	  "Type" : "String"
	}
  },
  
  "Resources":{
	    
	"DynamicSeleniumDockerInstance":{
      "Type":"AWS::EC2::Instance",
      "Properties":{
        "InstanceType": { "Ref" : "InstanceType" },
        "ImageId": "ami-e0fbee99",
        "SubnetId" : "subnet-0beb8d6c",
		"SecurityGroupIds" : ["sg-f9e20b82", "sg-7a483b01", "sg-07b7c47c"],
	    "Tags":[
		{
          "Key":"Userid",
          "Value": { "Ref" : "UserID" }
        },
        {
          "Key":"Vsad",
          "Value": { "Ref" : "AppID" }
        },
		{
          "Key":"Role",
          "Value": { "Ref" : "Role" }
        },		
        {
          "Key":"Name",
          "Value": { "Ref" : "InstanceName" }
        },
        {
          "Key":"nostop",
          "Value": ""
        }]
      }
    }
  },

  "Outputs":{
    "InstanceId":{
      "Description":"InstanceId of the newly created EC2 instance",
      "Value":{   "Ref":"DynamicSeleniumDockerInstance"  }
    },
    "AZ":{
      "Description":"Availability Zone of the newly created EC2 instance",
      "Value":{
        "Fn::GetAtt":[
          "DynamicSeleniumDockerInstance",
          "AvailabilityZone"
        ]
      }
    },
    "PrivateIP":{
      "Description":"PrivateIP of the newly created EC2 instance",
      "Value":{
        "Fn::GetAtt":[
          "DynamicSeleniumDockerInstance",
          "PrivateIp"
        ]
      }
    },
    "PrivateDnsName":{
      "Description": "Private DNS Name of the Instance",
      "Value":{
        "Fn::GetAtt":[
          "DynamicSeleniumDockerInstance",
          "PrivateDnsName"
        ]
      }
    }
  }
}

}
