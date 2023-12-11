.data
newline: .asciiz "\n"
.text
main:
.data
x: .asciiz "Miko sig relapse"
.text



li $v0, 4
la $a0, x
syscall


li $v0, 4
la $a0, newline
syscall


li $v0, 10
syscall

