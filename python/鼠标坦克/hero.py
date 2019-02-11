#_*_coding:utf-8_*_
'''
Created on 2013年9月22日

@author: javer
'''
'''连接到外面的世界'''
from substance import *
from weapons import *
import image_factory
#    --------------------------------我是华丽的分割线------------------------------------------------------------------------------------
'''图片地址'''
tank_image_filename = 'image/heros/tank.png'
ice_halo_image_filename = 'image/heros/halos/ice_halo.png'
poisoning_halo_image_filename = 'image/heros/halos/poisoning_halo.png'
fire_halo_image_filename = 'image/heros/halos/fire_halo.png'
armor_halo_image_filename = 'image/heros/halos/armor_halo.png'
life_halo_image_filename = 'image/heros/halos/life_halo.png'
'''图片处理器'''
image_factory = image_factory.ImageFactory()
'''得到图片集合'''
tank_image = image_factory.image_process(tank_image_filename, 1)
ice_halo = image_factory.image_process(ice_halo_image_filename, 1)[0]
poisoning_halo = image_factory.image_process(poisoning_halo_image_filename, 1)[0]
fire_halo = image_factory.image_process(fire_halo_image_filename, 1)[0]
armor_halo = image_factory.image_process(armor_halo_image_filename, 1)[0]
life_halo = image_factory.image_process(life_halo_image_filename, 1)[0]
#    --------------------------------我是华丽的分割线------------------------------------------------------------------------------------
class  WorldBridge():
    proManager = None
    Substance = None
    def __init__(self, proManager):
            WorldBridge.proManager = proManager
            
class Tank(Substance):      #英雄:坦克
    def __init__(self):
        Substance.__init__(self, WorldBridge.proManager.heros, tank_image)      
        self.name = 'tank'
        self.ap = 2
        self.armor = 1
        self.life = 10
        self.speed = 4.0
        self.rotation_speed = 0.36             
        self.position = Vector2(200, 200)
        #新属性
        self.isMove = 0                            #鼠标控制
        self.equipments = [Mine, SimpleShell, IceShell, SpecialShell, Tracking]
        self.equipment_kind = 0
        self.equipment =  self.equipments[0]        #装载的武器
        self.shellNum = 100                       #弹药数量
        self.ready_to_shoot = True                   #判断弹药装载完毕
        #暂时实例化武器以获得武器的信息,更换武器时一定要修改
        temp_equipment = self.equipment(self)
        self.equipment_name = temp_equipment.name
        self.equipment_ap = temp_equipment.ap
        temp_equipment.die()
        #定义属性条的颜色
        self.life_color = (255, 0, 0)#red
        self.speed_color = (44, 197, 44)#green
        self.armor_color = (29, 29, 167)#blue
        self.tank_ap_color = (237, 218, 41)#yellow
        self.weapon_ap_color = (174, 39, 182)#purple
        self.shellNum_color = (255, 255, 255)#black
    def prepare_move(self):
        #坦克旋转角度由其距离(鼠标的移动距离),坦克的旋转速度决定
        self.rotation_position -= pygame.mouse.get_rel()[0]  * self.rotation_speed
        Substance.prepare_move(self)
    def shoot(self):
        '''射击'''
        self.equipment(self)
        self.shellNum -= 1
    def equipment_change(self):#更换装备
        temp_equipment = self.equipment(self)
        self.equipment_name = temp_equipment.name
        self.equipment_ap = temp_equipment.ap
        temp_equipment.die()
    def render_statu(self):#这个需要更改
        #目前的状态
        life_rect = ((1200, 205), (self.life * 8, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.life_color, life_rect)       
        speed_rect = ((1200, 235), (self.speed * 10, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.speed_color, speed_rect)   
        armor_rect = ((1200, 270), (self.armor * 10, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.armor_color, armor_rect)
        tank_ap_rect = ((1200, 300), (self.ap * 10, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.tank_ap_color, tank_ap_rect)    
        weapon_ap_rect = ((1200, 330), (self.equipment_ap * 10, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.weapon_ap_color, weapon_ap_rect)
        shellNum_rect = ((1200, 360), (self.shellNum, 15))
        pygame.draw.rect(WorldBridge.proManager.screen, self.shellNum_color, shellNum_rect)
        
    def render_halo(self):#0代表正常状态, 1代表冰冻状态, 2代表中毒状态, 3加速, 4加护甲, 5加生命
        if self.statu == 0:
            pass
        elif self.statu == 1:
            WorldBridge.proManager.screen.blit(ice_halo, (self.position.x - 60, self.position.y - 60))
        elif self.statu == 2:
            WorldBridge.proManager.screen.blit(poisoning_halo, (self.position.x - 60, self.position.y - 60))
        elif self.statu == 3:
            WorldBridge.proManager.screen.blit(fire_halo, (self.position.x - 50, self.position.y - 50))
        elif self.statu == 4:
            WorldBridge.proManager.screen.blit(armor_halo, (self.position.x - 50, self.position.y - 50))
        elif self.statu == 5:
            WorldBridge.proManager.screen.blit(life_halo, (self.position.x - 55, self.position.y - 55))
    
    def run(self):
        self.render_halo()
        Substance.run(self)