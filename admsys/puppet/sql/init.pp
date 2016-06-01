class sql{

 package { 'mysql':
     ensure => present,
 }


 service { 'postfix' :
     ensure => running,
     enable => true,
 }
} 
