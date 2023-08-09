Ansible Playbook for 5G Catalogue

-Target machine: Ubuntu 16.04

-Install Ansible on Control Node (https://docs.ansible.com/ansible/latest/installation_guide/intro_installation.html)

-Edit the file /etc/ansible/hosts adding the target machines (the ones in which the 5G Catalogue will be installed):


```
all:
  hosts:
    target_machines:
  vars:
    ansible_become: true
    ansible_become_method: sudo
```

-Replace "hosts" on 5g_catalogue_playbook.yml with the target host/hosts (default: localhost)

-Launch ansible playbook
 
$ansible-playbook -v 5g_catalogue_playbook.yml
