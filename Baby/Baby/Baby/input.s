.data
newline: .asciiz "\n"
.text
main:

.data
  x: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 x


.data
  y: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 y



.data
x: .word 0
.text

    sw EmptyStack, x


    li $v0, 1
    lw $a0, x
    syscall


    li $v0, 4
    la $a0, newline
    syscall

