#_*_coding:utf-8_*_
'''
Created on 2013年9月22日

@author: javer
'''
'''连接到外面的世界'''
from substance import *
from weapons import *
import image_factory
import random
#    --------------------------------我是华丽的分割线------------------------------------------------------------------------------------
'''怪兽图片地址'''
#模板: _image_filename = monster_path + '
'''图片处理器'''
image_factory = image_factory.ImageFactory()
monster_path = 'image/monsters/'
#deep_sea
map = 'deep_sea/'
bubble_image_filename = monster_path + map + 'bubble.png'
cirrus_image_filename = monster_path + map +'cirrus.png'
crabman_image_filename = monster_path + map +'crabman.png'
cuttlefishwizard_image_filename = monster_path + map +'cuttlefishwizard.png'
fishman_image_filename = monster_path + map +'fishman.png'
fishguard_image_filename = monster_path + map +'fishguard.png'
lobster_image_filename = monster_path + map +'lobster.png'
scorpion_image_filename = monster_path + map +'scorpion.png'
sliverfish_image_filename = monster_path + map + 'sliverfish.png'
#deep_sea
bubble_image = image_factory.image_process(bubble_image_filename, 8)
cirrus_image = image_factory.image_process(cirrus_image_filename, 8)
crabman_image = image_factory.image_process(crabman_image_filename, 7)
cuttlefishwizard_image = image_factory.image_process(cuttlefishwizard_image_filename, 8)
fishguard_image = image_factory.image_process(fishguard_image_filename, 7)
fishman_image = image_factory.image_process(fishman_image_filename, 8)
lobster_image = image_factory.image_process(lobster_image_filename, 8)
scorpion_image = image_factory.image_process(scorpion_image_filename, 4)
sliverfish_image = image_factory.image_process(sliverfish_image_filename, 6)
#magic_forest
map = 'magic_forest/'
coconut_image_filename = monster_path + map +'coconut.png'
coral_image_filename = monster_path + map +'coral.png'
devil_image_filename = monster_path + map +'devil.png'
dinosaur_image_filename = monster_path + map +'dinosaur.png'
flower_image_filename= monster_path + map +'flower.png'
tree_image_filename = monster_path + map +'tree.png'
littledinosaur_image_filename = monster_path + map +'littledinosaur.png'
plant_image_filename = monster_path + map +'plant.png'
pumpkinman_image_filename = monster_path + map +'pumpkinman.png'
pupa_image_filename = monster_path + map +'pupa.png'
squirrel_image_filename = monster_path + map +'squirrel.png'
#magic_forest
coconut_image = image_factory.image_process(coconut_image_filename, 8)
coral_image = image_factory.image_process(coral_image_filename, 6)
devil_image = image_factory.image_process(devil_image_filename, 8)
dinosaur_image = image_factory.image_process(dinosaur_image_filename, 8)
littledinosaur_image = image_factory.image_process(littledinosaur_image_filename, 7)
plant_image = image_factory.image_process(plant_image_filename, 6)
pumpkinman_image = image_factory.image_process(pumpkinman_image_filename, 6)
pupa_image = image_factory.image_process(pupa_image_filename, 8)
squirrel_image = image_factory.image_process(squirrel_image_filename, 3)
flower_image = image_factory.image_process(flower_image_filename, 1)
tree_image =  image_factory.image_process(tree_image_filename, 1)
#seventeenth_street
map  = 'seventeenth_street/'
boneboy_image_filename = monster_path + map +'boneboy.png'
bonecaptain_image_filename = monster_path + map +'bonecaptain.png'
bonewizzard_image_filename = monster_path + map +'bonewizzard.png'
ghost_image_filename = monster_path + map +'ghost.png'
armourman_image_filename = monster_path + map +'armourman.png'
bullman_image_filename = monster_path + map +'bullman.png'
littleghost_image_filename = monster_path + map +'littleghost.png'
mummy_image_filename = monster_path + map +'mummy.png'
shortghost_image_filename = monster_path + map +'shortghost.png'
#seventeenth_street
boneboy_image = image_factory.image_process(boneboy_image_filename, 8)
bonecaptain_image = image_factory.image_process(bonecaptain_image_filename, 8)
bonewizzard_image = image_factory.image_process(bonewizzard_image_filename, 6)
ghost_image = image_factory.image_process(ghost_image_filename, 8)
armourman_image = image_factory.image_process(armourman_image_filename, 6)
littleghost_image = image_factory.image_process(littleghost_image_filename, 8)
bullman_image = image_factory.image_process(bullman_image_filename, 6)
shortghost_image = image_factory.image_process(shortghost_image_filename, 8)
mummy_image = image_factory.image_process(mummy_image_filename, 6)
#stone_city
map = 'stone_city/'
goldstoneman_image_filename = monster_path + map +'goldstoneman.png'#boss
stone_image_filename = monster_path + map +'stone.png'
stoneboy_image_filename = monster_path + map +'stoneboy.png'
stonedog_image_filename = monster_path + map +'stonedog.png'
stonegirl_image_filename = monster_path + map +'stonegirl.png'
stonefish_image_filename = monster_path + map +'stonefish.png'
stoneguard_image_filename = monster_path + map +'stoneguard.png'
stonelion_image_filename = monster_path + map +'stonelion.png'
stoneman_image_filename = monster_path + map +'stoneman.png'
#stone_city
goldstoneman_image = image_factory.image_process(goldstoneman_image_filename, 10)
stone_image = image_factory.image_process(stone_image_filename, 8)
stoneboy_image = image_factory.image_process(stoneboy_image_filename, 6)
stonedog_image = image_factory.image_process(stonedog_image_filename, 6)
stonegirl_image = image_factory.image_process(stonegirl_image_filename, 6)
stonefish_image = image_factory.image_process(stonefish_image_filename, 8)
stoneguard_image = image_factory.image_process(stoneguard_image_filename, 6)
stonelion_image = image_factory.image_process(stonelion_image_filename, 8)
stoneman_image = image_factory.image_process(stoneman_image_filename, 10)
#volcano
map = 'volcano/'
bat_image_filename = monster_path + map +'bat.png'
birdman_image_filename = monster_path + map +'birdman.png'
fireball_image_filename = monster_path + map +'fireball.png'
lavaman_image_filename = monster_path + map +'lavaman.png'
lavamonster_image_filename = monster_path + map +'lavamonster.png'
monkey_image_filename = monster_path + map +'monkey.png'
spider_image_filename = monster_path + map +'spider.png'
batman_image_filename = monster_path + map +'batman.png'
spidergirl_image_filename = monster_path + map +'spidergirl.png'
littlebat_image_filename = monster_path + map +'littlebat.png'
#volcano
bat_image = image_factory.image_process(bat_image_filename, 8) 
batman_image = image_factory.image_process(batman_image_filename, 8)
birdman_image = image_factory.image_process(birdman_image_filename, 8)
fireball_image = image_factory.image_process(fireball_image_filename, 8)
lavaman_image = image_factory.image_process(lavaman_image_filename, 6)
lavamonster_image = image_factory.image_process(lavamonster_image_filename, 8)
littlebat_image = image_factory.image_process(littlebat_image_filename, 8)
monkey_image = image_factory.image_process(monkey_image_filename, 6)
spider_image = image_factory.image_process(spider_image_filename, 8)
spidergirl_image = image_factory.image_process(spidergirl_image_filename, 6)
#    --------------------------------我是华丽的分割线------------------------------------------------------------------------------------

class  WorldBridge():
    proManager = None
    Substance = None
    def __init__(self, proManager):
            WorldBridge.proManager = proManager   
            
            
            
class Monster(Substance):       #抽象怪兽
    def __init__(self, iamges):
        Substance.__init__(self, WorldBridge.proManager.monsters, iamges)
        self.name = "monster"
        self.life = 1
        self.ap = 2
        self.armor = 1
        self.speed = 1
        self.position = Vector2(random.randint(50, WorldBridge.proManager.FIRELENGTH - 50), random.randint(50, WorldBridge.proManager.FIREHEIGHT - 50))#随机产生地址
    def prepare_move(self):
        #追踪坦克的方向,只要坦克移动后保持, 就一定会撞上
        x = WorldBridge.proManager.myTank.position.x - self.position.x
        y = -(WorldBridge.proManager.myTank.position.y - self.position.y)
        self.rotation_position = atan2(y, x) / pi * 180
        Substance.prepare_move(self)
'''        
1.bubble_image   cirrus_image   crabman_image   cuttlefishwizard_image   fishguard_image   fishman_image   lobster_image   scorpion_image   sliverfish_image
3.coconut_image   coral_image   devil_image   dinosaur_image   littledinosaur_image(f)   plant_image   pumpkinman_image   pupa_image   squirrel_image   flower_image  tree_image
4.boneboy_image   bonecaptain_image   bonewizzard_image   ghost_image   littleghost_image   shortghost_image  mummy_image
5.goldstoneman_image   stone_image   stoneboy_image   stonedog_image   stonegirl_image   stonefish_image   stoneguard_image   stonelion_image   stoneman_image
6.bat_image  batman_image   birdman_image   fireball_image   lavaman_image   lavamonster_image   littlebat_image   monkey_image   spider_image   spidergirl_image
'''  
class Test(Monster):#测试怪兽移动情况
    def __init__(self):
        Monster.__init__(self, spidergirl_image)
        self.name ='test'
        self.speed = 1
        
'''下面是魔法深林的怪'''
#coconut_image   coral_image   devil_image   dinosaur_image   littledinosaur_image(f)   plant_image   pumpkinman_image   pupa_image   squirrel_image   flower_image  tree_image
class Coconut(Monster):
    def __init__(self):
        Monster.__init__(self, coconut_image)
        self.name = 'coconut'
class Coral(Monster):
    def __init__(self):
        Monster.__init__(self, coral_image)
        self.name = 'coral'
        self.speed = 2
class Devil(Monster):
    def __init__(self):
        Monster.__init__(self, devil_image)
        self.name = 'devil'
        self.life = 2
class Dinosaur(Monster):
    def __init__(self):
        Monster.__init__(self, dinosaur_image)
        self.name = 'dinosaur'
        self.ap = 3
class Plant(Monster):
    def __init__(self):
        Monster.__init__(self, plant_image)
        self.name = 'plant'
        self.speed = 2
        self.life = 2
class Pumpkinman(Monster):
    def __init__(self):
        Monster.__init__(self, pumpkinman_image)
        self.name = 'pumpkinman'
        self.ap = 3
class Pupa(Monster):
    def __init__(self):
        Monster.__init__(self, pupa_image)
        self.name = 'pupa'
        self.life = 5
class Squirrel(Monster):
    def __init__(self):
        Monster.__init__(self, squirrel_image)
        self.name = 'squirrel'
        self.life = 4
        self.ap = 3
class Tree(Monster):                #怪兽:Boss, 树妖
    '''怪兽:树木'''#可以发射炮弹
    def __init__(self):
        Monster.__init__(self, tree_image)
        self.name = 'tree'
        self.life = 16
        self.position = Vector2(500, 500)
        self.equipment = SpecialShell
        self.shoot_prepare_time = 30     #装填弹药的准备时间:大于30的以30为一秒计算, 小于10的以没1为1秒计算
    def move_with_change(self):
        Monster.move_with_change(self)
        self.realse_skill()
    def realse_skill(self):
            if WorldBridge.proManager.time_counter % self.shoot_prepare_time == 0:#time_counter和time_pass不同
                monster_shell = self.equipment(self)
                WorldBridge.proManager.weapons.append(monster_shell)
'''下面是深蓝海的怪兽'''
class Bubble(Monster):
    def __init__(self):
        Monster.__init__(self, bubble_image)
        self.life = 3
class Cirrus(Monster):
    def __init__(self):
        Monster.__init__(self, cirrus_image)
        self.name = 'cirrus'
        self.ap = 3
class Crabman(Monster):
    def __init__(self):
        Monster.__init__(self, crabman_image)
        self.name = 'crabman'
        self.ap = 3
        self.life = 2
class Fishguard(Monster):
    def __init__(self):
        Monster.__init__(self, fishguard_image)
        self.name = 'fishguard'
        self.speed = 4
class Fishman(Monster):
    def __init__(self):
        Monster.__init__(self, fishman_image)
        self.name = 'fishman'
        self.armor = 3
class Lobster(Monster):
    def __init__(self):
        Monster.__init__(self, lobster_image)
        self.name = 'lobster'
        self.speed = 3
        self.ap = 3
class Scorpion(Monster):
    def __init__(self):
        Monster.__init__(self, scorpion_image)
        self.name = 'scorpion'
        self.life = 6
class Sliverfish(Monster):
    def __init__(self):
        Monster.__init__(self, sliverfish_image)
        self.name = 'sliverfish'
        self.ap = 5
class Cuttlefishwizard(Monster):#boss
    def __init__(self):
        Monster.__init__(self, cuttlefishwizard_image)
        self.name = 'cuttlefishwizard'
        self.life = 16
        self.speed = 2
    def same_ground_collide(self):
        self.realse_skill()
        Monster.same_ground_collide(self)          
    def realse_skill(self):#召唤技能
        if self.life > 5:#生命值大于10,召唤怪兽
            Bubble()
        else :
            self.life += 1
            Bubble()
            
'''下面是第十七街的怪兽'''
 #3.boneboy_image   bonecaptain_image   bonewizzard_image   ghost_image   littleghost_image   shortghost_image  mummy_image  
class Boneboy(Monster):
    def __init__(self):
        Monster.__init__(self, boneboy_image)
        self.name = 'boneboy'
        self.life = 3
class Bonecaptain(Monster):
    def __init__(self):
        Monster.__init__(self, bonecaptain_image)
        self.name = 'bonecaptain'
        self.life = 4
class Bonewizzard(Monster):
    def __init__(self):
        Monster.__init__(self, bonewizzard_image)
        self.name = 'bonewizzard'
        self.life = 4
        self.speed = 2
class Ghost(Monster):
    def __init__(self):
        Monster.__init__(self, ghost_image)
        self.name = 'ghost'
        self.ap = 4
class Littleghost(Monster):
    def __init__(self):
        Monster.__init__(self, littleghost_image)
        self.name = 'littleghost'
        self.life = 3
        self.ap = 3
class Shortghost(Monster):
    def __init__(self):
        Monster.__init__(self, shortghost_image)
        self.name = 'shortghost'
        self.armor = 4
class Bullman(Monster):
    def __init__(self):
        Monster.__init__(self, bullman_image)
        self.name = 'bullman'
        self.ap = 3
        self.armor = 3
class Armourman(Monster):#boss
    def __init__(self):
        Monster.__init__(self, armourman_image)
        self.name = 'armourman'
        self.life = 4
        self.ap = 3
        self.speed = 2
class Mummy(Monster):#Boss木乃伊,可以召唤小怪
    def __init__(self):
        Monster.__init__(self, mummy_image)
        self.name = 'mummy'
        self.life = 16
        self.shoot_prepare_time = 120
    def move_with_change(self):
        Monster.move_with_change(self)
        self.realse_skill()
    def realse_skill(self):#召唤技能
        if WorldBridge.proManager.time_counter % self.shoot_prepare_time == 0:
            temp_monster_id =  random.randint(0, 2)
            if temp_monster_id == 0:
                Boneboy()
            elif temp_monster_id == 1:
                Bonecaptain()
            elif temp_monster_id == 2:
                Bonewizzard()
'''下面是石头城的怪兽'''
#goldstoneman_image   stone_image   stoneboy_image   stonedog_image   stonegirl_image   stonefish_image   stoneguard_image   stonelion_image   stoneman_image
class Stone(Monster):
    def __init__(self):
        Monster.__init__(self, stone_image)
        self.name = 'stone'
        self.life = 10
class Stoneboy(Monster):
    def __init__(self):
        Monster.__init__(self, stoneboy_image)
        self.name = 'stoneboy'
        self.life = 8
        self.speed = 2
class Stonedog(Monster):
    def __init__(self):
        Monster.__init__(self, stonedog_image)
        self.name = 'stonedog'
        self.speed = 4
        self.life = 4
class Stonegirl(Monster):
    def __init__(self):
        Monster.__init__(self, stonegirl_image)
        self.name = 'stonegirl'
        self.ap = 5
class Stonefish(Monster):
    def __init__(self):
        Monster.__init__(self, stonefish_image)
        self.name = 'stonefish'
        self.ap = 5
        self.speed = 4
class Stoneguard(Monster):
    def __init__(self):
        Monster.__init__(self, stoneguard_image)
        self.name = 'stoneguard'
        self.speed = 5
class Stonelion(Monster):
    def __init__(self):
        Monster.__init__(self, stonelion_image)
        self.name = 'stonelion'
        self.speed = 3
        self.ap = 4
        self.life = 4
class Stoneman(Monster):
    def __init__(self):
        Monster.__init__(self, stoneman_image)
        self.name = 'stoneman'
        self.life = 2
        self.armor = 5
class Goldstoneman(Monster):
    def __init__(self):
        Monster.__init__(self, goldstoneman_image)
        self.name = 'goldstoneman' 
        self.life = 16
        self.equipment = SimpleShell     
        self.shoot_prepare_time = 2
    def move_with_change(self):
        Monster.move_with_change(self)
        self.realse_skill()
    def realse_skill(self):
            if WorldBridge.proManager.pass_time % self.shoot_prepare_time == 0:
                monster_shell = self.equipment(self)
                WorldBridge.proManager.weapons.append(monster_shell)
'''下面是火山口的怪兽'''
#bat_image  batman_image   birdman_image   fireball_image   lavaman_image   lavamonster_image   littlebat_image   monkey_image   spider_image   spidergirl_image
class Bat(Monster):
    def __init__(self):
        Monster.__init__(self, bat_image)
        self.name = 'bat'
class Batman(Monster):
    def __init__(self):
        Monster.__init__(self, batman_image)
        self.name = 'batman'
class Birdman(Monster):
    def __init__(self):
        Monster.__init__(self, birdman_image)
        self.name = 'birdman'
class Fireball(Monster):
    def __init__(self):
        Monster.__init__(self, fireball_image)
        self.name = 'fireball'
class Lavaman(Monster):
    def __init__(self):
        Monster.__init__(self, lavaman_image)
        self.name = 'lavaman'
class Lavamonster(Monster):
    def __init__(self):
        Monster.__init__(self, lavamonster_image)
        self.name = 'lavamonster'
class Littlebat(Monster):
    def __init__(self):
        Monster.__init__(self, littlebat_image)
        self.name = 'littlebat'
class Monkey(Monster):
    def __init__(self):
        Monster.__init__(self, monkey_image)
        self.name = 'monkey'
class Spider(Monster):
    def __init__(self):
        Monster.__init__(self, spider_image)
        self.name = 'spider'
class Spidergirl(Monster):
    def __init__(self):
        Monster.__init__(self, spidergirl_image)
        self.name = 'spidergirl'
'''
class (Monster):
    def __init__(self):
        Monster.__init__(self, _image)
        self.name = ''
'''    

class Flower(Monster):          #怪兽:小花, 无速度, 无攻击力, 为赛车模式提供方便
    def __init__(self, position):
        Monster.__init__(self, flower_image)
        self.name = 'flower'
        self.speed = 0
        self.position = position
    def render_statu(self):
        pass
class Person(Monster):              #自定义的怪兽,为了支持个人头像录入
    def __init__(self, iamges):
        Monster.__init__(self, iamges)
        self.name = 'person'
        self.life = 10
        self.speed = 2          