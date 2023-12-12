.data
newline: .asciiz "\n"
.text
main:

.data
x: .word 2
.text

lw $t0, x


.data
y: .word 1
.text

lw $t1, y


.data
z: .word 3
.text

lw $t2, z

add $t3 $t1 $t2
sub $t4 $t0 $t3

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

