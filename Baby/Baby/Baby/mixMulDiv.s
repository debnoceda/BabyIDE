.data
newline: .asciiz "\n"
.text
main:

.data
x: .word 9
.text

lw $t0, x


.data
y: .word 3
.text

lw $t1, y


.data
z: .word 10
.text

lw $t2, z

mul $t3 $t1 $t2

div $t0 $t3
mflo$t4


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

