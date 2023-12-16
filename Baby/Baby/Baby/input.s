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
    lw $t0, x


.data
  y: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 y
    lw $t1, y

    add $t2 $t0 $t1
    sw $t2, x


    li $v0, 1
    lw $a0, x
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

