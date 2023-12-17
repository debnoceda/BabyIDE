.data
newline: .asciiz "\n"
.text
main:

.data
statement_32: .asciiz "Enter x: "
.text
    li $v0, 4
    la $a0, statement_32
    syscall


.data
  x: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 x
    lw $t0, x

    li $t1 10

.data
a: .word 10
.text

    lw $t2, a


.data
statement_33: .asciiz "Enter x: "
.text
    li $v0, 4
    la $a0, statement_33
    syscall


.data
  y: .word 0
.text

    li $v0, 5
    syscall
    sw $v0 y
    lw $t3, y

    li $t4 100

.data
z: .word 100
.text

    lw $t5, z

    mul $t6 $t3 $t5
    div $t6 $t2
    mflo $t4
    add $t7 $t0 $t4
    sub $t8 $t7 $t3

.data
ans: .word 0
.text

    sw $t8, ans


.data
statement_34: .asciiz "The answer is: "
.text
    li $v0, 4
    la $a0, statement_34
    syscall


    li $v0, 1
    lw $a0, ans
    syscall


    li $v0, 4
    la $a0, newline
    syscall

