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

mul $t2 $t0 $t1

.data
ans: .word 0
.text

sw $t2, ans


li $v0, 1
lw $a0, ans
syscall


li $v0, 4
la $a0, newline
syscall
