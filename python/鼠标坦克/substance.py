#_*_ coding:gbk _*_
'''
Created on 2013年9月19日

@author: javer
'''
import pygame
from gameobjects.vector2 import Vector2
from math import *
import random
import time

'''连接到外面的世界'''
class  WorldBridge():
    proManager = None
    Substance = None
    time = time.time()
    def __init__(self, proManager):
            WorldBridge.proManager = proManager            
          
class Substance():              #神:万物之母
    def __init__(self,  group, images):       
        #物理属性
        self.name = 'substance'                   #名字
        self.ap = 0                                      #攻击力
        self.life =  1                                  #生命值 
        self.speed = 0                             #移动速度
        self.armor = 0                              #护甲  
        self.position = Vector2(0, 0)            #在地图上的(x, y)坐标
        self.next_position = Vector2(0, 0)
        self.rotation_position = 0                #旋转的位置, 横坐标为0
        self.rotation_speed = 0                   #旋转的速度
        #sprite的最基本三个要素(images, rect, group)
        self.images = images
        self.image = images[0]                            #物体的图片
        self.images_index = 0                                #记录当前图片的索引
        self.images_length = len(images)                 #记录图片一共有多少张
        self.width = self.image.get_width()                   #碰撞核心的长度和高度
        self.height = self.image.get_height()
        self.rect = pygame.Rect(self.position, (self.width, self.height))                #物体真实的大小()
        self.group = group                                 #物体所在的组,便于区分其总体作用
        self.group.append(self)         
        self.base_group = WorldBridge.proManager.substances     #便于总的碰撞计算(世间万物皆可碰撞)
        self.base_group.append(self)
        #后来添加的状态,以表明现在角色的属性
        self.last_time = 0#作用时间,单位秒
        self.statu = 0  #0代表正常状态, 1代表冰冻状态, 2代表中毒状态, 3加速, 4护甲, 5加血       
    def run(self):#怪兽运动的总流程
        if self.speed != 0:
            self.prepare_move()
            self.move_with_change()
        else:
            self.move_without_change()
        '''移动后执行下面的运算'''
        self.render_statu()         #显示血条
        if self.group == WorldBridge.proManager.heros or self.group == WorldBridge.proManager.monsters:
            self.attack()
        #self.render_rect(self.rect)                 #用来检查真实的碰撞面职  
    def change_statu(self):
        if self.last_time > 0:
            self.last_time = self.last_time - 1
        #各种BUFF反应
            if self.statu == 1:#减速
                if self.last_time >= 4:
                    self.speed -= 1
                else :
                    self.speed +=1
            elif self.statu == 2:#中毒
                self.life = self.life -1
                if self.life <= 0:
                    self.die()#修补一下死得慢的缘故
            elif self.statu == 3:#加速
                if self.last_time >= 4:
                    self.speed += 1
                else:
                    self.speed -= 1
            elif self.statu == 4:#加护甲
                if self.last_time >= 4:
                    self.armor += 1
                else:
                    self.armor  -= 1
            elif self.statu == 5:#加血
                self.life += 0.5
        else:
            self.statu = 0
            self.last_time = 0
    def change_image(self):     #改变图片
        if self.images_length != 1:
            self.image = self.images[self.images_index]
            self.images_index += 1
            if self.images_index == self.images_length:
                self.images_index = 0       
    def prepare_move(self):             #计算得出下次移动的位置
        self.next_position = self.position.copy() 
        move_x = cos(self.rotation_position * pi / 180)
        move_y = sin(self.rotation_position * pi / 180)
        move_distance = Vector2(move_x, -move_y)#y加符号和计算机的坐标有关
        if self.group == WorldBridge.proManager.heros: #坦克特殊处理
            move_distance *= self.isMove  
        self.next_position = self.next_position + move_distance * self.speed       #得到物体即将移动的位置
        self.out_of_border()
    def out_of_border(self):#出界后的处理  
        '''出界后的处理'''
        if self.next_position.x < 10:
            self.next_position.x = 10
        if self.next_position.x > WorldBridge.proManager.FIRELENGTH - 25:
            self.next_position.x = WorldBridge.proManager.FIRELENGTH - 25
        if self.next_position.y < 10:
            self.next_position.y = 10
        if self.next_position.y > WorldBridge.proManager.FIREHEIGHT - 10:
            self.next_position.y = WorldBridge.proManager.FIREHEIGHT - 10  
    def move_with_change(self):             #在显示上作图,并改变碰撞核心的位置      
        rotated_image = pygame.transform.rotate(self.image, self.rotation_position)         #旋转图画 
        w, h = rotated_image.get_size()                  #旋转后的尺寸
        draw_position = Vector2(self.next_position.x  - w / 2 , self.next_position.y  - h / 2)                    #旋转后的位置
        self.rect = pygame.Rect((self.position.x - w/4, self.position.y - h/4) , (self.width / 2, self.height / 2))    
        self.position = self.next_position                #改变现在位置,以改变碰撞核心的位置
        WorldBridge.proManager.screen.blit(rotated_image, draw_position)                         #最后局部画到画板上                
    def move_without_change(self):
        WorldBridge.proManager.screen.blit(self.image, self.position)    
        self.rect = pygame.Rect(self.position , (self.width, self.height))          
    def attack(self):   #检查碰撞,假如碰撞,将调用effect()方法起各种作用
        for substance in WorldBridge.proManager.substances:
            if self.rect.colliderect(substance.rect) and self != substance:
                if self.group != substance.group :#不是同一组的
                    substance.effect(self)
                else:#同族的撞到了一起, 随机产生新的位置
                    self.same_ground_collide()
    def same_ground_collide(self):#同族碰撞产生的效果,这个为怪兽重新出现重新避开坦克有关联
        temp_position_x = WorldBridge.proManager.myTank.position.x
        temp_position_y = WorldBridge.proManager.myTank.position.y
        if temp_position_x > WorldBridge.proManager.FIRELENGTH / 2:
            appear_x = random.randint(50, WorldBridge.proManager.FIRELENGTH /2)
        else:
            appear_x = random.randint(WorldBridge.proManager.FIRELENGTH / 2, WorldBridge.proManager.FIRELENGTH  - 50)
        if temp_position_y > WorldBridge.proManager.FIREHEIGHT / 2:
            appear_y = random.randint(50, WorldBridge.proManager.FIREHEIGHT / 2)
        else:
            appear_y = random.randint(WorldBridge.proManager.FIREHEIGHT / 2, WorldBridge.proManager.FIREHEIGHT - 50)
        self.position = Vector2(appear_x, appear_y)
    def effect(self, effect_object):#相当于普通攻击,后面重写就是技能伤害
        self.life -=(effect_object.ap - self.armor)
        effect_object.life -= (self.ap - effect_object.armor)
        if self.life <= 0:       #已死,最后remove由另一个方法处理
            self.die()
        if effect_object.life <= 0:
            effect_object.die()           
    def die(self):                      #把自己从世间排除掉
        self.group.remove(self)
        self.base_group.remove(self)
    def render_statu(self):           #显示英雄和怪物的血条, 以及现在的BUFF
        if self.group != WorldBridge.proManager.weapons and self.group != WorldBridge.proManager.buildings:        #非武器非建筑才显示血条
            life_position = self.position - Vector2(self.width / 2 , self.height / 2)
            life_rect = (life_position, (self.life * 10, 5))
            life_color = (255, 0, 0)#red
            pygame.draw.rect(WorldBridge.proManager.screen, life_color, life_rect)           
    def render_rect(self, life_rect):               #检查真实的碰撞核心的位置和面职
        #life_position = (self.position.x, self.position.y)#血条位置
        life_color = (0, 0, 255)#蓝色
        pygame.draw.rect(WorldBridge.proManager.screen, life_color, life_rect)
