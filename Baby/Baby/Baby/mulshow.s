.data
newline: .asciiz "\n"
.text
main:

li $v0, 4
la $a0, newline
syscall


.data
statement_0: .asciiz "hehe"
.text
li $v0, 4
la $a0, statement_0
syscall


li $v0, 4
la $a0, newline
syscall


li $v0, 4
la $a0, newline
syscall


.data
statement_1: .asciiz "Mikooo"
.text
li $v0, 4
la $a0, statement_1
syscall


li $v0, 4
la $a0, newline
syscall


li $v0, 10
syscall

