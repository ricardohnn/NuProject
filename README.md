# NuProject

I will update the README with some thoughts about implementation which i could have used but i didn't (for n-reasons) use and
also some links.

1 - The Http/json request consumer

How to test a request before really implementing it?

https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop?hl=en

I really enjoy this plugin, it makes the "first glance" of the problems a little bit easier (at least for me).

Ok, I won't talk about http and asyncs, but i will discard robospice since volley can basically do the same.
But on the meantime, some new names came up "okHttp" and "retrofit". Retrofit seems to be faster than volley (which i rather
prefer and the one i will use, since it is maintained by Google - high five to that):

http://stackoverflow.com/questions/16902716/comparison-of-android-networking-libraries-okhttp-retrofit-volley

Also, retrofit supports okHttp (which i still haven't a reason to use) but seems like jake Wharton made possible to use volley
with okHttp by extending HurlStack (any further explanations about volley can be found in here i think- haven't read it all):

http://www.programering.com/a/MTO3UzMwATk.html
https://plus.google.com/+JakeWharton/posts/eJJxhkTQ4yU
https://gist.github.com/JakeWharton/5616899

Some nice comparison is also in here:

https://medium.com/android-news/android-networking-i-okhttp-volley-and-gson-72004efff196#.hyuz1ufrm
http://instructure.github.io/blog/2013/12/09/volley-vs-retrofit/

So i went with volley and gson (i will make comments about going with jackson because, as far as i remember, jackson was a little
bit harder to work with, and honestly, right now, i don't have time).

Just some old thought that came to my mind right now, when i was doing an application for 99taxi/easytaxi job interview i have seen
something about flatbuffers, which i added the following remark:

http://frogermcs.github.io/json-parsing-with-flatbuffers-in-android/
"sounds interesting but won't be checking it right now, but good for performance improvement
(maybe changing from json to flatbuffers would be good in server side also)".

2 - Layouts

I am changing the view pager created by Android studio default since it has a lot of deprecated actionbar stuff, but i will be using
Google's swipe view example:

http://developer.android.com/training/implementing-navigation/lateral.html 

Seems to fit perfectly for my needs right now.
Ok, so Nu requested a HEADER in the layout, which i really don't understand what they would consider a header, i think it just means
that it won't be scrollable together with the listview (which IMHO, it basically takes around 40% of the screen with lots of spacing
- which makes it pretty - but, if a user would scroll to see the list, then i think the list should take the focus in here, so i would
think about resizing the total payment box - but never, never hide it - and hide the "gerar boleto" together with the month's total
- which seems to be a little duplicated(?) from the box information).

Nu uses a horizontal scroll view for the dates inside a framelayout (with a back button, which I won't be using since... i have no
parent =T to go back to), but the dumpview does not show anything regarding the changing color's arrow, so i might guess it has some
image being set the same colors as the text.

Using apktool, it seems that i have found about it in activity_bill_detail.xml:
 <com.nu.custom_ui.pager_triangle.PagerSlidingTabStrip android:layout_gravity="center" android:id="@id/tabs" android:paddingLeft="0.0dip" android:paddingTop="12.0dip" android:paddingRight="0.0dip" android:paddingBottom="4.0dip" android:layout_width="fill_parent" android:layout_height="wrap_content" app:pstsPaddingMiddle="true" />

Well, we will keep it simple (which means i will focus on treating the logics first).

3 - The parsing

Now that's a huge parsing, I am kinda glad to use volley right now, since getting all these in the first screen would be the best to do
(I think...).

This seems really an interesting link:

http://www.javacreed.com/gson-deserialiser-example/

Maybe i will read it if i have a free time.
I am able to retrieve the json, but href gives me a null pointer. Although it is correct, something seems fishy.

Geez, i just realized that not all the fields are required, so rather than returning null, they do not keep the json stable, therefore the
fields can/will change (facepalm).
I need to make a check on which fields are required, right now, i am able to parse everything and show it in a scrollview, so...
getting/parsing the json is finished, just missing some details.

4 - The db

Ok, so today i was checking how to send the information forward. I've thought that making it parcelable would be a good choice, but...
everytime keeping a array of bill objects, seems a little... stupid? So i will be saving them inside a db, which would be the correct
solution to begin with. I will use the same beans (or at least reuse) that i generated for the json parsing with gson, but instead of
making everything serializable, i will make some id fields not serializable (aka transient).

http://www.javacreed.com/gson-annotations-example
http://camelcode.org/tutorial/Exclude-fields-from-JSON-with-java-modifiers-using-Gson.htm
http://camelcode.org/tutorial/Exclude-fields-from-JSON-using-Gson-with-@Expose.htm#82

Merry xmas! finally the db is partially done! I started by creating the DB as usual, but a friend of mine said that he used a nice lib to
make things easier. Sugar ORM!
And what a piece of s* that was... Come on devs, make libs and make documentation... it was painful to make it almost work (almost a day)
and then, i realized that i needed to check if sugar ORM was the best choice, since relations seemed really... lacking...
I've made a small research and found out that GreenDAO and DBFlow seemed to be the best choices for SQL:

http://www.raizlabs.com/dev/2015/02/go-dbflow-fastest-android-orm-database-library/

I know that the research was done by the devs of dbflow but they seemed to convince me to use the dbflow.
And... more painful stuff to do before everything got settled. Android Studio seems to has some issues with gradle build, and was showing
an strange error. I needed to go to File>invalidate caches/restart, in order to fix the errors that was showing, update everything and then,
just then, see the real error.
The id of a class, which was used as a primary key and exported as a foreign key to other classes, had the private modifier. But hey... i've
done my getters and setters... but, that was not enough! You must remove the private modifier in order to make it work.

I wasted almost... 2 days with DBFlow, 1 with sugar orm and half a day with usual db implementation.
I hope to have everything working until monday... oh... and the unit tests...

I am now saving to the db correctly... well... almost.
At first glance, i have not seen that there are no ID for some of the bill input. I mean... why?! Just why?
I will now analyze what fields are missing (let's just considerer that any field inside bill can be empty), but for that, i have to search
for a better sql viewer, just to make things easier.
SqliteSpy seems pretty much ok for now, but is so annoying to keep dumping the db everytime.
Since i don't have much time, i will use genymotion (nexus 5 configs) and just a simple script to dump and send as a parameter to sqlitespy:

import os

os.system("rm ./nuProjectDatabase.db")
os.system("adb wait-for-device; adb pull /data/data/com.rdzero.nuproject/databases/nuProjectDatabase.db ./nuProjectDatabase.db")
os.system("/Downloads/SQLiteSpy_1.9.10/SQLiteSpy.exe ./nuProjectDatabase.db &")
os.system("adb shell pm clear com.rdzero.nuproject")

That will be enough for now. I will need to re-run the app everytime to save the db, but it is easier to check any modification to the db.