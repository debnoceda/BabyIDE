.data
newline: .asciiz "\n"
.text
main:

li $v0, 4
la $a0, newline
syscall


li $v0, 10
syscall

