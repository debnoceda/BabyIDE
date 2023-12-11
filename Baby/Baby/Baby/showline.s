.data
newline: .asciiz "\n"
.text
main:

.data
x: .word 1
.text



li $v0, 4
la $a0, x
syscall


li $v0, 4
la $a0, newline
syscall


li $v0, 10
syscall

