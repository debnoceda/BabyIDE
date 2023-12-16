.data
newline: .asciiz "\n"
.text
main:

.data
statement_0: .asciiz "Enter x: "
.text
    li $v0, 4
    la $a0, statement_0
    syscall


.data
  x: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 x
    lw $t0, x


.data
statement_1: .asciiz "Enter y: "
.text
    li $v0, 4
    la $a0, statement_1
    syscall


.data
  y: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 y
    lw $t1, y


.data
statement_2: .asciiz "Enter z: "
.text
    li $v0, 4
    la $a0, statement_2
    syscall


.data
  z: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 z
    lw $t2, z

    mul $t3 $t1 $t2
    add $t4 $t0 $t3

.data
ans: .word 0
.text

    sw $t4, ans


    li $v0, 1
    lw $a0, ans
    syscall


    li $v0, 4
    la $a0, newline
    syscall


    li $v0, 10
    syscall

