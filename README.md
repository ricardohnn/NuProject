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

